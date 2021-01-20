package com.project.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.dao.UserDAO;
import com.project.model.User;

@FixMethodOrder
public class UserDaoTest {
	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	static UserDAO ud;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ud = appContext.getBean("userDao", UserDAO.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		ud.h2InitDao();
		
	}

	@After
	public void tearDown() throws Exception {
		
//		ud.h2DestroyDao();
		
	}

//	@Ignore
	@Test
	public void getAllUsersTest() {
		
		List<User> uList = ud.readAllUsers();
		
		System.out.println("TEST RESULT");
		System.out.println(uList);
		
		assertEquals(1, uList.get(0).getUserId());
		assertEquals(2, uList.get(1).getUserId());
		assertEquals(3, uList.get(2).getUserId());
		
		assertEquals("Joe", uList.get(0).getFirstName());
		
		
		
	}
	
//	@Ignore
	@Test
	public void readByEmailTest() {
		
		//User u = ud.readUserByEmail("j@mail.com");
		User u2 = ud.readUserByEmail("s@mail.com");
		User u3 = ud.readUserByEmail("a@mail.com");
		
		//assertEquals("Joe", u.getFirstName());
		//assertEquals(1, u.getUserId());
		assertEquals("Sean", u2.getFirstName());
		assertEquals("Albert", u3.getFirstName());
	}
	
//	@Ignore
	@Test
	public void updateEmailTest() {
		User u = ud.readUserByEmail("j@mail.com");
		
		ud.updateEmail(u.getUserId(), "jshan@mail.com");
		
		User uAfter = ud.readUserByID(u.getUserId());
		assertEquals("jshan@mail.com", uAfter.getEmail());
	}
	
	@Ignore
	@Test
	public void deleteUserTest() {
		List<User> uList = ud.readAllUsers();
		
		ud.deleteUser(uList.get(0));
		
		List<User> uAfter = ud.readAllUsers();
		
		assertEquals(3, uList.size());
		assertEquals(2, uAfter.size());
	}
	
	@Test
	public void createUserTest() {
		
		List<User> uList = ud.readAllUsers();
		User u = new User("t", "tsalt", "pass", "Trevin", "Chester", "t@mail.com", null);
		ud.insertUser(u);
		
		assertEquals(uList.size(), ud.readAllUsers().size()-1);
		
		
	}

}
