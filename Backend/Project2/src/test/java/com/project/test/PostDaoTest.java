package com.project.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.dao.PostDAO;
import com.project.dao.UserDAO;
import com.project.model.Post;
import com.project.model.User;

@FixMethodOrder
public class PostDaoTest {
	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	static UserDAO ud;
	static PostDAO pd;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ud = appContext.getBean("userDao", UserDAO.class);
		pd = appContext.getBean("postDao", PostDAO.class);
		
		ud.h2InitDao();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void readAllPostsTest() throws Exception {
		
		List<Post> allPosts = pd.readAllPosts();
		
		assertEquals(4, allPosts.size());
		
	}
	
	@Test
	public void readPostsByUserTest() throws Exception {
		
		List<Post> posts = pd.readAllPostByUserId(1);
		System.out.println(posts.get(0));
		assertEquals(1, posts.size());
		assertEquals("Post Text", posts.get(0).getPostText());
		assertEquals(null, posts.get(0).getPostPicture());
	}
	
//	@Ignore
	@Test
	public void updateTextTest() throws Exception {
		
		pd.updateText(1, "Post Text");
		
		Post p = pd.readPostByID(1);
		
		assertEquals("Post Text", p.getPostText());
		
	}
	
//	@Ignore
	@Test
	public void createPostTest() throws ValidationException, SQLException, Exception {
		
		User u = ud.readAllUsers().get(0);
		
		List<Post> initial = pd.readAllPosts();
		
		Post p = new Post(u, "some text", null);
		
		pd.createPost(p);
		
		System.out.println("RESULTS OF CREATE POST TEST");
		System.out.println(initial.size());
		System.out.println(pd.readAllPosts().size());
		
		assertEquals(initial.size(), pd.readAllPosts().size()-1);
		
		
	}

}
