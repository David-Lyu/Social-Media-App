package com.project.maindriver;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.dao.LikeDAO;
import com.project.dao.PostDAO;
import com.project.dao.UserDAO;
import com.project.hasher.PBDKF2Hasher;
import com.project.model.Like;
import com.project.model.Post;
import com.project.model.User;
import com.project.service.LikeService;
import com.project.service.PostService;
import com.project.service.UserService;


public class MainDriver {

	public static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

//	
	public static UserDAO udi = appContext.getBean("userDao", UserDAO.class);
	public static PostDAO pdi = appContext.getBean("postDao", PostDAO.class);
	public static LikeDAO ldi = appContext.getBean("likeDao", LikeDAO.class);
//	public static PostService ps = appContext.getBean("postServ", PostService.class);

	public static UserService usi = appContext.getBean("userServ", UserService.class);
	public static PostService psi = appContext.getBean("postServ", PostService.class);
	public static LikeService lsi = appContext.getBean("likeServ", LikeService.class);
//	public static PostDAO appDao = appContext.getBean("postDao", PostDAO.class);

	public static void main(String[] args) {
		
//////UNCOMMENT THIS OUT IF YOU HAVE NOT INITIALIZED THE DB ALSO IN THE CFG FILE CHANGE hbm2ddl.auto to CREATE!!!!!!
//		insertInitialValues();
		
		//System.out.println(udi.readUserByID(2));
		//udi.updatePassword(1, "joe");
		//pdi.updateText(1, "joe did this");
		//System.out.println("\n\t" + udi.readUserByUsername("david"));
//		System.out.println("\n\t\t" + pdi.readAllPosts());
//		System.out.println("\n\t" + pdi.readPostByID(1));
//		System.out.println("\n\t\t" + pdi.readAllPostByUserId(1));
		//System.out.println("\n\t" + udi.readAllUsers());
		//System.out.println("\n\t" + pdi.readAllPosts());
		//System.out.println("\n\t" + ldi.readAllLikes());
		
		//System.out.println(usi.getAllUsers());
		//System.out.println(pdi.readAllPosts());
		
		//testing password hashing functionality
		//PBDKF2Hasher hasher = new PBDKF2Hasher();
		
//		byte[] byteArr = new byte[16];
//		
//		String string1 = new String(byteArr);
//		System.out.println(string1);
//		
//		//System.out.println(byteArr.toString());
//		
//		SecureRandom random = new SecureRandom();
//		
//		random.nextBytes(byteArr);
//		String string2 = new String(byteArr);
//		
//		System.out.println(string2);
//		//System.out.println(byteArr.toString());
		
		System.out.println(usi.getAllUsers());
		
		System.out.println(psi.getAllPosts());
		
		System.out.println(lsi.getAllLikes());
		//System.out.println(psi.getAllPosts());
		//System.out.println(psi.addPost(new Post(usi.getAllUsers().get(0), "", null)));
		//System.out.println(psi.addPost(new Post(usi.getAllUsers().get(0), null, null)));
		
//		try
//		{
//			psi.addPost(new Post(udi.readAllUsers().get(0), null, null));
//			pdi.createPost(new Post(udi.readAllUsers().get(0), null, null));
//		}
//		catch(Exception e)
//		{
//			System.out.println("Exception in driver. You know the one.");
//			//e.printStackTrace();
//		}
		
//		System.out.println(usi.getAllUsers());
		
		
	}

	public static void insertInitialValues() {
		
		String p1 = "password";
		String p2 = "pass";
		PBDKF2Hasher hasher = new PBDKF2Hasher();
		String s1 = hasher.newSalt();
		String s2 = hasher.newSalt();
		
		String hp1 = hasher.hash(p1, s1);
		String hp2 = hasher.hash(p2, s2);

		
		usi.addUser(new User("jshan", s2, hp2, "joe", "shannon", "j.w.shannon@hotmail.com", null));
		usi.addUser(new User("dlyu", s1, hp1, "david", "lyu", "d@mail.com", null));
		usi.addUser(new User("awash", s2, hp2, "albert", "washington", "a@mail.com", null));
		
		psi.addPost(new Post(udi.readUserByID(1), "joe post", null));
		psi.addPost(new Post(udi.readUserByID(2), "david post", null));
		
		lsi.addNewLike(new Like(psi.getPostByID(1), udi.readUserByID(2)));
		lsi.addNewLike(new Like(psi.getPostByID(2), udi.readUserByID(1)));
		
		usi.createPasswordResetTokenForUser(usi.getUserByEmail("j.w.shannon@hotmail.com"), UUID.randomUUID().toString());
		
		//usi.addUser(new User("david", s1, hp1, "david", "lyu", "d@mail.com", null));
		//usi.addUser(new User("jshan", s2, hp2, "joe", "shannon", "j@mail.com", null));
		
//		usi.addUser(new User("david", hp1,"asdf","david","lyu","d@mail.com", null));
//		usi.addUser(new User("david1", hp2 ,"asdf","david","lyu","dav@mail.com", null));
		//psi.addPost(new Post(udi.readUserByID(1), "1234545", null));
		//lsi.addNewLike(new Like(psi.getPostByID(1), udi.readUserByID(1)));
		//lsi.addNewLike(new Like(psi.getPostByID(1), udi.readUserByID(2)));


//		udi.insertUser(new User("david","asdf","david","lyu","d@mail.com", null));
//		udi.insertUser(new User("david1","asdf","david","lyu","dav@mail.com", null));
//		pdi.createPost(new Post(1, udi.readUserByID(1), "1234545", null));
	}
}
