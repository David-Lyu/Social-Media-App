package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.PasswordResetTokenDAO;
import com.project.dao.UserDAO;
import com.project.model.PasswordResetToken;
import com.project.model.User;

@Service("userServ")
public class UserServiceImpl implements UserService {

	// FIELDS
	private UserDAO uDao;
	private PasswordResetTokenDAO pResetDao;

	public UserServiceImpl() {

	}

	// AUTOWIRED CONSTRUCTOR
	@Autowired
	public UserServiceImpl(UserDAO uDao, PasswordResetTokenDAO pResetDao) {
		this.uDao = uDao;
		this.pResetDao = pResetDao;
	}

	public UserDAO getuDao() {
		return uDao;
	}

	public void setuDao(UserDAO uDao) {
		this.uDao = uDao;
	}

	@Override
	public boolean addUser(User user) {

		return uDao.insertUser(user);
	}

	@Override
	public List<User> getAllUsers() {

		return uDao.readAllUsers();
	}

	@Override
	public User getUserByID(int userID) {

		return uDao.readUserByID(userID);
	}

	@Override
	public User getUserByUsername(String username) {

		return uDao.readUserByUsername(username);
	}

	@Override
	public User getUserByEmail(String email) {
		return uDao.readUserByEmail(email);
	}

	@Override
	public boolean updateUsername(int user_id, String username) {

		return uDao.updateUsername(user_id, username);
	}

	@Override
	public boolean updateEmail(int user_id, String email) {

		return uDao.updateEmail(user_id, email);
	}

	@Override
	public boolean updatePassword(int user_id, String password, String salt) {

		return uDao.updatePassword(user_id, password, salt);
	}

	@Override
	public boolean updatePicture(int user_id, String picture) {

		return uDao.updatePicture(user_id, picture);
	}

	@Override
	public boolean removeUser(User user) {

		return uDao.deleteUser(user);
	}

	@Override
	public boolean verifyCredentials(String username, String password) {

		User chkUser = uDao.readUserByUsername(username);

		if (chkUser != null && chkUser.getPassword().equals(password)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkUsernameExists(String username) {
		// The long way. Probably not proper.

		List<User> uList = uDao.readAllUsers();
		List<String> unList = new ArrayList<String>();

		for (User u : uList) {
			unList.add(u.getUsername());
		}

		if (unList.contains(username)) {
			return true;
		}

		return false;
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {

		PasswordResetToken myToken = new PasswordResetToken(token, user);

		pResetDao.addTokenToDB(myToken);

	}

}
