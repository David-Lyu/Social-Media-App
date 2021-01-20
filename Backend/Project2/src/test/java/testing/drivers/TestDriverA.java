//package testing.drivers;
//
//import java.util.List;
//
//import com.project.dao.PostDAO;
//import com.project.dao.PostDAOImpl;
//import com.project.dao.UserDAO;
//import com.project.dao.UserDAOImpl;
//import com.project.model.Post;
//import com.project.model.User;
//import com.project.service.UserService;
//import com.project.service.UserServiceImpl;
//import com.project.util.HibernateUtil;
//
//public class TestDriverA {
//
//	public static PostDAO pdi = new PostDAOImpl();
//	public static UserDAO udi = new UserDAOImpl();
//	public static UserService us = new UserServiceImpl();
//	
//	public static void main(String[] args) 
//	{
//		HibernateUtil.setUrl(System.getenv("REVATURE_DB_URL") + "Project2");
//		HibernateUtil.setUsername(System.getenv("REVATURE_DB_USERNAME"));
//		HibernateUtil.setPassword(System.getenv("REVATURE_DB_PASSWORD"));
//		
//		insertInitialValues();
//		
//		
//		List<User> grandList = udi.readAllUsers();
//		
//		System.out.println("\n\tTesting User Dao get all");
//		System.out.println(udi.readAllUsers());
//		
//		System.out.println("\n\tTest User Dao get by ID");
//		displayUser(grandList.get(1));
//		
//		System.out.println("\n\tTesting User Dao get by un");
//		displayUser(udi.readUserByUsername("WashiWashi"));
//		
//		System.out.println("\n\t Testing user service All users");
//		System.out.println(us.getAllUsers());
//		
//		
//		List<Post> pList = pdi.readAllPosts();
//		
//		
//	}
//
//	public static void insertInitialValues() {
//		udi.insertUser(new User("david","asdf","david","lyu","d@mail.com", null));
//		udi.insertUser(new User("david1","asdf","david","lyu","dav@mail.com", null));
//		us.addUser(new User("WashiWashi", "test", "Albert", "Washington", "a.washington@hotmail.com", null));
//		User newUser = new User("Jacob", "test", "Jacob", "Haerle", "Haer.AOL.com", null);
//		us.addUser(newUser);
//		System.out.println(newUser);
//		
//		System.out.println("ABOUT TO GET JACOB!!!!!-------------------------------");
//		System.out.println(us.getUserByUsername("Jacob"));
//		User j = us.getUserByUsername("Jacob");
//		
//		pdi.createPost(new Post(j, "Hi it's me Jacob", null));
////		pdi.createPost(new Post(us.getUserByUsername("Jacob"), "Blah blah blah", null));
////		
//	}
//	
//	private static void displayUser(User user)
//	{
//		System.out.println("User:");
//		System.out.println("User ID: " + user.getUserId());
//		System.out.println("Username: " + user.getUsername());
//		System.out.println("Password: " + user.getPassword());
//		System.out.println("First Name: " + user.getFirstName());
//		System.out.println("Last Name: " + user.getLastName());
//		System.out.println("Email: " + user.getEmail());
//		System.out.println();
//		
//	}
//
//}
