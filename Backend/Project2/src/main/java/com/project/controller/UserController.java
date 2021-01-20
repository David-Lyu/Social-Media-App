package com.project.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.dao.PasswordResetTokenDAO;
import com.project.hasher.PBDKF2Hasher;
import com.project.model.GenericResponse;
import com.project.model.PasswordResetToken;
import com.project.model.User;
import com.project.service.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	// FIELDS
	@Autowired
	private UserService usi;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MessageSource messages;
	@Autowired
	private Environment env;
	@Autowired
	private PasswordResetTokenDAO passResetDao;

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	public UserController(UserService useServ, JavaMailSender mailSender, MessageSource messages, Environment env) {
		super();
		this.usi = useServ;
		this.mailSender = mailSender;
		this.messages = messages;
		this.env = env;
	}

	/**
	 * This method returns the user and creates a session
	 * 
	 * @param session - this is the HttpSession object that Spring will inject for
	 *                us
	 * @param newUser - this is the user JSON that will be added to the DB and
	 *                subsequently added to the session
	 */
	@PostMapping("/newUser")
	public @ResponseBody ResponseEntity<String> createUser(HttpSession session, @RequestBody User newUser) {

		/*
		 * IN ORDER FOR THIS METHOD TO FUNCTION APPROPRIATELY WE NEED TO MAKE SURE ALL
		 * REQUISITE FORMS HAVE BEEN VALIDATED ON THE FRONT END.
		 * 
		 * I can add checks here if need be, but I think it'll be more efficient to have
		 * those checks be done BEFORE the newUser object is sent through as a JSON
		 * 
		 * - Joe
		 */
		User u = usi.getUserByUsername(newUser.getUsername());

		// checking if user by that name already exists
		if (u != null) {
			return new ResponseEntity<String>("false", HttpStatus.CONFLICT);
		}

		// this is where we hash their requested password
		// we also need to store the salt that we create for this process

		// creating the hasher instance and getting a new random array of bytes which we
		// call "salt"
		PBDKF2Hasher hasher = new PBDKF2Hasher();
		String salt = hasher.newSalt();

		String password = newUser.getPassword();

		String hashedPassword = hasher.hash(password, salt);

		// setting the relevant user attributes BEFORE passing it to the DB
		newUser.setSalt(salt);
		newUser.setPassword(hashedPassword);

		// actually passing the user with the new hashed password to the DB
		boolean gotUser = usi.addUser(newUser);

		if (gotUser == false || newUser == null)
			return null;

		return new ResponseEntity<String>("true", HttpStatus.CREATED);

	}

	/**
	 * This method is sent by a GET request and gets the username from the
	 * parameters and will find that user in the database. It will return a JSON of
	 * the user object except for the password.
	 * 
	 * @param session   - this is the HttpSession object that Spring will inject for
	 *                  us
	 * @param userToGet - this is the user JSON that will be gotten from the DB,
	 *                  added to the session and returned if found
	 */
	@GetMapping("/getUser")
	public @ResponseBody User getUser(HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");

		System.out.println("In /getuser endpoint " + user);

		if (user == null) {
			return null;
		}

		return user;

	}

	@GetMapping("/getAllUsers")
	public @ResponseBody List<User> getAllUsers() {
		System.out.println("In /getAllUsers endpoint");
		System.out.println(usi.getAllUsers());
		return usi.getAllUsers();
	}

	@PostMapping("/login")
	public @ResponseBody ResponseEntity<User> login(HttpSession session, @RequestBody User userToLogin) {

		// making a call to the DB to see if the account with that username exists
		User u = usi.getUserByEmail(userToLogin.getEmail());
		System.out.println(u);
		if (u == null) {
			// maybe use another HttpStatus to send back here...so as to differentiate this
			// error
			// from the login credentials being invalid if the account DOES exist.
			return new ResponseEntity<User>(u, HttpStatus.EXPECTATION_FAILED);
		}

		String username = u.getUsername();
		String password = userToLogin.getPassword();
		String salt = u.getSalt();

		// new hasher instance and hashing the password to compare with the one gotten
		// from the DB
		PBDKF2Hasher hasher = new PBDKF2Hasher();

		String hashedPassword = hasher.hash(password, salt);

		boolean bool = usi.verifyCredentials(username, hashedPassword);

		if (bool) {
			session.setAttribute("loggedInUser", u);
			System.out.println("In /login endpoint: " + session.getAttribute("loggedInUser"));
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		} else {
			u = null;
			return new ResponseEntity<User>(u, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updateUser")
	public @ResponseBody ResponseEntity<String> updateUser(HttpSession session, @RequestBody User user) {

		System.out.println(user.getUserId());
		System.out.println(user.getEmail());
		System.out.println(user.getUsername());

		// return new ResponseEntity<String>("true", HttpStatus.ACCEPTED);

		boolean b1 = false;
		boolean b2 = false;

		if (user.getEmail() != "") {
			b1 = usi.updateEmail(user.getUserId(), user.getEmail());
		}

		if (user.getUsername() != "") {
			b2 = usi.updateUsername(user.getUserId(), user.getUsername());
		}

		if (b1 || b2) {
			return new ResponseEntity<String>("true", HttpStatus.OK);
		}

		return new ResponseEntity<String>("false", HttpStatus.CONFLICT);

	}
	
	@PutMapping("updatePassword")
	public ResponseEntity<String> updatePassword(@RequestBody User user){
		
		//instantiating a hasher and getting a new salt for the user
		PBDKF2Hasher hasher = new PBDKF2Hasher();
		String salt = hasher.newSalt();
		
		//making new hashed password
		String hashedPass = hasher.hash(user.getPassword(), salt);
		
		//call to service layer to update the user's info
		boolean didUpdate = usi.updatePassword(user.getUserId(), hashedPass, salt);
		
		if(didUpdate) {
			return new ResponseEntity<String>("Password update successful", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Did not update", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<GenericResponse> resetPassword(HttpServletRequest req, @RequestParam("email") String userEmail) {
		User user = usi.getUserByEmail(userEmail);

		if (user != null) {
			String token = UUID.randomUUID().toString();
			usi.createPasswordResetTokenForUser(user, token);
			mailSender.send(constructResetTokenEmail(getAppUrl(req), req.getLocale(), token, user));
		}
		
		System.out.println("in password reset, email sender endpoint");
		System.out.println(req.getRequestURI());

		//put inside return statement
		return new ResponseEntity<GenericResponse>(new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, "Reset your password.", req.getLocale())), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/changePassword")
	public String showChangePasswordPage(Locale locale, Model model, @RequestParam("token") String token) {
		
		//deleting tokens that have expired before pulling the current one
		final Calendar cal = Calendar.getInstance();
		passResetDao.deleteExpiredTokensFromDB(cal.getTime());
		
		String result = validatePasswordResetToken(token);
		
		if(result != null) {
			String message = messages.getMessage("auth.message."+result, null, locale);
			return "redirect:http://bing.com";
		}else {
			model.addAttribute("token", token);
			return "redirect:http://localhost:3000/changePassword?token="+token;
		}
	}
	
	@GetMapping("/getUserByEmail")
	public ResponseEntity<User> getUserByEmail(@RequestParam String email){
		
		User u = usi.getUserByEmail(email);
		
		if(u != null) {
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		}
		
		
		return new ResponseEntity<User>(u, HttpStatus.NOT_FOUND);
		
	}


	/*
	 * Below are some non-API methods to help with sending the password reset email
	 */

	private String getAppUrl(HttpServletRequest req) {
		return "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
	}

	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale,
			final String token, final User user) {
		final String url = contextPath + "/api/user/changePassword?token=" + token;
		final String message = messages.getMessage("message.resetPassword", null, locale);
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}
	
	private String validatePasswordResetToken(String token) {
		final PasswordResetToken passToken = passResetDao.findTokenFromDB(token);
		
		return !isTokenFound(passToken) ? "invalidToken" 
				: isTokenExpired(passToken) ? "expired"
						:null;
		
	}
	
	private boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}
	
	private boolean isTokenExpired(PasswordResetToken passToken) {
		final Calendar cal = Calendar.getInstance();
		return passToken.getExpiryDate().before(cal.getTime());
	}
	
	
	

}
