package com.project.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.dao.LikeDAO;
import com.project.dao.PostDAO;
import com.project.model.Like;
import com.project.model.Post;

@Service("postServ")
public class PostServiceImpl implements PostService {
	
	final static Logger loggy = Logger.getLogger(PostServiceImpl.class);

	private PostDAO pDao;
	private LikeDAO lDao;
	
	//No arg Constructors
	public PostServiceImpl() 
	{
		
	}
	
	//Constructors
	@Autowired
	public PostServiceImpl(PostDAO pDao, LikeDAO lDao) 
	{
		this.pDao = pDao;
		this.lDao = lDao;
	}

	//Setters/Getters
	public PostDAO getpDao() 
	{
		return pDao;
	}




	public void setpDao(PostDAO pDao) {
		this.pDao = pDao;
	}


	
	//Implementation
	
	/**
	 * Adds specified post object to the database through the Data Access Object <br>
	 * returns true if presumed success. 
	 * @author Albert
	 * 
	 * @param post The Post object
	 * @return boolean <b>true</b> if no sql or validation exception was thrown. The text of a post in the Database has a length of VARCHAR(280)
	 * 
	 */
	@Override
	public boolean addPost(Post post) 
	{
		if(post.getPostText() == null || post.getPostText().length() == 0)
		{
			loggy.error("The post text can not be null or empty. Post update failed.");
			return false;
		}

		//DataIntegrityViolationException
		//ConstraintViolationException
		
		try
		{
			pDao.createPost(post);
		}
		catch(ValidationException e)
		{
			// Violating validation annotations in Post Dao should cause this.
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		return true;
	}

	/**
	 * Gets all posts from the database through the Post Data Access Object.
	 * @author Albert
	 * 
	 * @return a list of Posts 
	 */
	@Override
	public List<Post> getAllPosts()
	{
		try
		{
			List<Post> pList = pDao.readAllPosts();
			return pList;
		}
		catch(Exception e)
		{
			return null;
		}
		  
	}
	

	/**
	 * Gets a single Post through the Post Data Access Object layer 
	 * representing a post record in the database.
	 * 
	 * @author Albert
	 * 
	 * @param postId the ID that represents a specific post in the database
	 * @return A Post object representing a record in the database
	 * 
	 */
	@Override
	public Post getPostByID(int postId) 
	{
		try
		{
			if(postId < 0)
			{
				loggy.error("The argument \"postId\" was less than 0. Presumed failure.");
				return null;
			}
			Post post =  pDao.readPostByID(postId);
			return post;
		}
		catch(Exception e)
		{
			return null;
		}

	}
	
	/**
	 * Gets all posts from the database through the Post Data Access Object the belong to the specified user.
	 * @author Albert
	 * 
	 * @param userId the user's ID
	 * @return a list of Posts made by the user
	 */
	@Override
	public List<Post> getAllPostsByUser(int userId)
	{
		try
		{
			List<Post> pList = pDao.readAllPostByUserId(userId);
			return pList;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * Updates a single Post's text through the Post Data Access Object layer 
	 * in the database.
	 * 
	 * @author Albert
	 * 
	 * @param postId the ID that represents a specific post in the database
	 * @param postText a string representing the text of a post. PostText cannot be null, empty, or larger than 280 characters.
	 * @return <b>true</b> if operation succeeded. An exception is presumed failure.
	 * 
	 */
	@Override
	public boolean updateText(int postId, String postText) 
	{
		//No ID should be less than 0
		if(postId < 0)
		{
			loggy.error("The argument \"postId\" was less than 0. Presumed failure.");
			return false;
		}
		
		//postText should not be empty or null
		if(postText == null || postText.length() == 0)
		{
			loggy.error("The post text can not be null or empty. Post update failed.");
			return false;
		}
		
		//Could also put logic to keep text from exceeding 280 characters, but already being controlled with an annotation

		try
		{
			return pDao.updateText(postId, postText);
		}
		catch(Exception e)
		{
			return false;
		}
		
	}

	
	/**
	 * Updates a picture associated with a post through the Post Data Access Object layer.
	 * 
	 * @author Albert
	 * 
	 * @param postId the ID that represents a specific post in the database
	 * @param picture A byte array containing the binary data of an image.
	 * @return <b>true</b> if operation was successful.
	 * 
	 */
	@Override
	public boolean updatePicture(int postId, String picture) 
	{
		
		//No ID should be less than 0
		if(postId < 0)
		{
			loggy.error("The argument \"postId\" was less than 0. Presumed failure.");
			return false;
		}
		
		try
		{
			return pDao.updatePicture(postId, picture);
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	
	
	/**
	 * <b>CURRENTLY NOT IMPLEMENTED</b> <br>
	 * Updates a likes associated with a post through the Post Data Access Object layer.
	 * 
	 * @author Albert
	 * 
	 * @param postId the ID that represents a specific post in the database
	 * @param picture A byte array containing the binary data of an image.
	 * @return <b>true</b> if operation was successful.
	 * 
	 */
	@Override
	public boolean updateLikes(int postId, int userId) 
	{
		//No ID should be less than 0
		if(postId < 0 || userId < 0)
		{
			loggy.error("The argument \"postId\" or \"userId\" was less than 0. Presumed failure.");
			return false;
		}
		
		try
		{
			return pDao.updateLikes(postId, userId);
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	/**
	 * 
	 * Removes a post and all of it likes.
	 * 
	 * @author Albert
	 * 
	 * @param post A post in the database
	 * @return <b>true</b> if operation was successful.
	 * 
	 */
	//Update later when bidirectional association is implemented.
	@Override
	public boolean removePost(Post post) 
	{
		//No ID should be less than 0
		if(post == null)
		{
			loggy.error("The Post is null, therefore nothing will be removed.");
			return false;
		}
		
		//Remove all likes from the post
		List<Like> lList = lDao.readLikeByPost(post.getPostId());
		
		if(!lList.isEmpty())
		{
			for(Like l : lList)
			{
				lDao.deleteLike(l);
			}
		}
		
		
		//Delete post
		
		try
		{
			return pDao.deletePost(post);
		}
		catch(ConstraintViolationException e)
		{
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
		
		
	}
}
