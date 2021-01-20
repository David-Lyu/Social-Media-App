package com.project.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.model.Post;

@Repository("postDao")
@Transactional
public class PostDAOImpl implements PostDAO {

	
	private SessionFactory sesFact; //this is null initially. Set by spring.
	
	//CONSTRUCTORS
	
	public PostDAOImpl() 
	{
		
	}
	
	@Autowired
	public PostDAOImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	@Override
	public void createPost(Post post) throws SQLException, ValidationException, Exception 
	{
		sesFact.getCurrentSession().save(post);
		
	}

	
	//IMPLEMENTATION
	
	
	
	/**
	 * Selects all posts from the database.
	 * @author Albert
	 * 
	 * @return List of posts
	 * 
	 */
	@Override
	public List<Post> readAllPosts() throws Exception
	{
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<Post> cr = cb.createQuery(Post.class);

		Root<Post> root = cr.from(Post.class);
		//changing to order posts by timestamps descending
		cr.orderBy(cb.desc(root.get("timestamp")));

		Query<Post> query = ses.createQuery(cr);
		List<Post> results = query.getResultList();

		return results;
	}

	@Override
	public Post readPostByID(int post_id) throws SQLException
	{
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<Post> cr = cb.createQuery(Post.class);

		Root<Post> root = cr.from(Post.class);

		cr.select(root).where(cb.equal(root.get("postId"), post_id));

		Query<Post> query = ses.createQuery(cr);
		List<Post> results = query.getResultList();

		if (results.size() == 0) {
			return null;
		}

		return results.get(0);
	}

	////////////////////////////////////////// readPostByUser
	@Override
	public List<Post> readAllPostByUserId(int userId) throws SQLException
	{
		// Boilerplate session start
		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<Post> cr = cb.createQuery(Post.class);

		Root<Post> root = cr.from(Post.class);

		// select criteria
		cr.select(root).where(cb.equal(root.get("author"), userId));
		
		//changing to order posts by timestamps descending
		cr.orderBy(cb.desc(root.get("timestamp")));

		Query<Post> query = ses.createQuery(cr);
		List<Post> results = query.getResultList();

		if (results.size() == 0) {
			return null;
		}

		return results;
	}

	@Override
	public boolean updateText(int postID, String postText) throws SQLException, ValidationException 
	{

		Session ses = sesFact.getCurrentSession();
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		CriteriaUpdate<Post> cUpdate = cb.createCriteriaUpdate(Post.class);
		Root<Post> root = cUpdate.from(Post.class);
		cUpdate.set("postText", postText);
		cUpdate.where(cb.equal(root.get("postId"), postID));

		//Transaction t = ses.beginTransaction();
		int updateCnt = ses.createQuery(cUpdate).executeUpdate();
		//t.commit();
		
		//Check if any rows updated
		if(updateCnt > 0)
		{
			return true;
		}
		
		return false;


	}

	@Override
	public boolean updatePicture(int post_id, String picture) throws SQLException {


		//HAVE NOT YET TESTED THIS ONE, BUT THEORETICALLY IT SHOULD WORK
		Session ses = sesFact.getCurrentSession();
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		CriteriaUpdate<Post> cUpdate = cb.createCriteriaUpdate(Post.class);
		Root<Post> root = cUpdate.from(Post.class);
		cUpdate.set("postPicture", picture);
		cUpdate.where(cb.equal(root.get("postId"), post_id));

		//Transaction t = ses.beginTransaction();
		int updateCnt = ses.createQuery(cUpdate).executeUpdate();
		//t.commit();
		
		//Check if any rows updated
		if(updateCnt > 0)
		{
			return true;
		}
		
		return false;
		
	}

	@Override
	public boolean updateLikes(int post_id, int user_id) throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println("Not implemented yet.");
		return false;

	}
	
	
	@Override
	public boolean deletePost(Post post) throws SQLException, ConstraintViolationException
	{
		Session ses = sesFact.getCurrentSession();
		CriteriaBuilder cb = ses.getCriteriaBuilder();

		CriteriaDelete<Post> cDelete = cb.createCriteriaDelete(Post.class);
		Root<Post> root = cDelete.from(Post.class);
		
		cDelete.where(cb.equal(root.get("postId"), post.getPostId()));

		//Transaction t = ses.beginTransaction();
		int updateCnt = ses.createQuery(cDelete).executeUpdate();
		//t.commit();
		
		//Check if any rows updated
		if(updateCnt > 0)
		{
			return true;
		}
		
		return false;
		
	}

}
