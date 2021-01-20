package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.model.User;

@Repository("userDao")
@Transactional
public class UserDAOImpl implements UserDAO {
	
	//testing fields
	public static String testUrl = "jdbc:h2:./folder1/theData;MODE=PostgreSQL";
	public static String testUser = "sa";
	public static String testPass = "sa";
	

	private SessionFactory sesFact; // initially null

	public UserDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public UserDAOImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	// @Autowired
	public void setSesFact(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}

	@Override
	public boolean insertUser(User user) {
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		ses.save(user);
		return true;
	}

	@Override
	public List<User> readAllUsers() {
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);

		Root<User> root = cr.from(User.class);
		cr.select(root);

		Query<User> query = ses.createQuery(cr);
		List<User> results = query.getResultList();

		// Business logic

		return results;
	}

	@Override
	public User readUserByID(int userID) {
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);

		Root<User> root = cr.from(User.class);

		cr.select(root).where(cb.equal(root.get("userId"), userID));

		Query<User> query = ses.createQuery(cr);
		List<User> results = query.getResultList();


		if (results.size() == 0) {
			return null;
		}

		return results.get(0);
	}

	@Override
	public User readUserByEmail(String email) {
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);

		Root<User> root = cr.from(User.class);

		cr.select(root).where(cb.equal(root.get("email"), email));

		Query<User> query = ses.createQuery(cr);
		List<User> results = query.getResultList();

		if (results.size() == 0) {
			return null;
		}

		return results.get(0);
	}

	@Override
	public User readUserByUsername(String username) {
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);

		Root<User> root = cr.from(User.class);

		cr.select(root).where(cb.equal(root.get("username"), username));

		Query<User> query = ses.createQuery(cr);
		List<User> results = query.getResultList();

		if (results.size() == 0) {
			return null;
		}
		// System.out.println(results);

		return results.get(0);
	}

	@Override
	public boolean updateUsername(int user_id, String username) {

		Session ses = sesFact.getCurrentSession();
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		CriteriaUpdate<User> cUpdate = cb.createCriteriaUpdate(User.class);
		Root<User> root = cUpdate.from(User.class);
		cUpdate.set("username", username);
		cUpdate.where(cb.equal(root.get("userId"), user_id));

		int updateCnt = ses.createQuery(cUpdate).executeUpdate();

		if (updateCnt > 0) {
			return true;
		}

		return false;

	}

	@Override
	public boolean updateEmail(int user_id, String email) {
		Session ses = sesFact.getCurrentSession();
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		CriteriaUpdate<User> cUpdate = cb.createCriteriaUpdate(User.class);
		Root<User> root = cUpdate.from(User.class);
		cUpdate.set("email", email);
		cUpdate.where(cb.equal(root.get("userId"), user_id));

		int updateCnt = ses.createQuery(cUpdate).executeUpdate();

		if (updateCnt > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePassword(int user_id, String password, String salt) {
		Session ses = sesFact.getCurrentSession();
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		CriteriaUpdate<User> cUpdate = cb.createCriteriaUpdate(User.class);
		Root<User> root = cUpdate.from(User.class);
		cUpdate.set("password", password);
		cUpdate.set("salt", salt);
		cUpdate.where(cb.equal(root.get("userId"), user_id));

		int updateCnt = ses.createQuery(cUpdate).executeUpdate();

		if (updateCnt > 0) {
			return true;
		}

		return false;

	}

	@Override
	public boolean updatePicture(int userID, String picture) {
		// get Hibernate session
		Session ses = sesFact.getCurrentSession();
		// Get the criteria builder
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		// Create update criteria
		CriteriaUpdate<User> cUpdate = cb.createCriteriaUpdate(User.class);

		// Set up the From table
		Root<User> root = cUpdate.from(User.class);

		// Set statement. Can have duplicates.
		cUpdate.set("profilePicture", picture);

		// Set filter (Where)
		cUpdate.where(cb.equal(root.get("userId"), userID));

		// Transaction t = ses.beginTransaction();
		int updateCnt = ses.createQuery(cUpdate).executeUpdate();
//		t.commit();

		// Check if any rows updated
		if (updateCnt > 0) {
			return true;
		}

		return false;

	}

	@Override
	public boolean deleteUser(User user) {
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		// Business logic
		ses.delete(user);

		return true;
	}

	@Override
	public void h2InitDao() {

		try(Connection conn = DriverManager.getConnection(testUrl, testUser, testPass)){
			
//			//creating the users table
//			String userSQL = "CREATE TABLE users ( user_id serial PRIMARY KEY " + ", email varchar " + ", username varchar"
//					+ ", firstname varchar " + ", lastname varchar "+ ", password_salt varchar " + ", password varchar "
//					+ ", profile_picture varchar);";
//			
//			Statement userState = conn.createStatement();
//			userState.execute(userSQL);
//			
//			//create the posts table
//			String postSQL = "CREATE TABLE posts (post_id serial PRIMARY KEY " + ", post_text varchar " + ", post_picture varchar "
//					+ ", creation_time timestamp " + ", user_id_fk integer, FOREIGN KEY (user_id_fk) REFERENCES users (user_id) ON DELETE CASCADE);";
//			
//			Statement postState = conn.createStatement();
//			postState.execute(postSQL);
//			
//			//create the likes table
//			String likeSQL = "CREATE TABLE likes (like_id serial PRIMARY KEY " + ", post_id_fk integer " + ", user_id_fk integer "
//					+ ", FOREIGN KEY (post_id_fk) REFERENCES posts (post_id) ON DELETE CASCADE " + ", FOREIGN KEY (user_id_fk) REFERENCES users (user_id) ON DELETE CASCADE);";
//			
//			Statement likeState = conn.createStatement();
//			likeState.execute(likeSQL);
			
			
			/////////////////////////////////////////////////////////////
			//POPULATING TABLES WITH VALUES
			
//			User joe = new User("jshan", "saltforjoe", "passforjoe", "Joe", "Shannon", "j@mail.com", null);
//			this.insertUser(joe);
//			
//			User sean = new User("sriehl", "saltforsean", "passforsean", "Sean", "Riehl", "s@mail.com", null);
//			this.insertUser(sean);
//			
//			User albert = new User("awashington", "saltforalbert", "passforalbert", "Albert", "Washington", "a@mail.com", null);
//			this.insertUser(albert);
			
			
//			//populating users table
			String insertUserSQL = "INSERT INTO users (email, username, firstname, lastname, password_salt, password, profile_picture) "
					+ "VALUES ('j@mail.com', 'jshan', 'Joe', 'Shannon', 'saltforjoe', 'passforjoe', null);" 
					+ "INSERT INTO users (email, username, firstname, lastname, password_salt, password, profile_picture) "
					+ "VALUES ('s@mail.com', 'sriehl', 'Sean','Riehl', 'saltforsean', 'passforsean', null);"
					+ "INSERT INTO users (email, username, firstname, lastname, password_salt, password, profile_picture) "
					+ "VALUES ('a@mail.com', 'awashington', 'Albert', 'Washington', 'saltforalbert', 'passforalbert', null);";
			
			Statement insertUserState = conn.createStatement();
			insertUserState.execute(insertUserSQL);
			
			String insertPostSQL = "INSERT INTO posts (post_id, post_text, post_picture, timestamp, user_id_fk) "
					+ "VALUES (1,'Joes Post', null, CURRENT_TIMESTAMP, 1);"
					+ "INSERT INTO posts (post_id, post_text, post_picture, timestamp, user_id_fk) "
					+ "VALUES (2, 'Seans Post', null, CURRENT_TIMESTAMP, 2);"
					+ "INSERT INTO posts (post_id, post_text, post_picture, timestamp, user_id_fk) "
					+ "VALUES (3, 'Alberts Post', null, CURRENT_TIMESTAMP, 3);";
			
			Statement insertPostState = conn.createStatement();
			insertPostState.execute(insertPostSQL);
			
			String insertLikeSQL = "INSERT INTO likes (post_id_fk, user_id_fk) "
					+ "VALUES (1, 1);"
					+ "INSERT INTO likes (post_id_fk, user_id_fk) "
					+ "VALUES (1, 2);"
					+ "INSERT INTO likes (post_id_fk, user_id_fk) "
					+ "VALUES (1, 3);";
//			
			Statement insertLikeState = conn.createStatement();
			insertLikeState.execute(insertLikeSQL);
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void h2DestroyDao() {

		try (Connection conn = DriverManager.getConnection(testUrl, testUser, testPass)){
			
			String sql = "DROP TABLE likes;" + "DROP TABLE posts;" + "DROP TABLE passwordresettoken;" + "DROP TABLE users;";
			
			Statement state = conn.createStatement();
			state.execute(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
