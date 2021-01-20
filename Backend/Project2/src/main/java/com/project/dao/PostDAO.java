package com.project.dao;

import java.sql.SQLException;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.project.model.Post;

public interface PostDAO {
	
	// Create
	public void createPost(Post post) throws SQLException, ValidationException, Exception;
	
	// Read
	public List<Post> readAllPosts() throws Exception;
	public Post readPostByID(int postId) throws Exception;
	public List<Post> readAllPostByUserId(int userId) throws Exception;
	
	// Update
	public boolean updateText(int postId, String postText) throws Exception; // TODO: Figure out these methods
	public boolean updatePicture(int postId, String picture) throws Exception;
	public boolean updateLikes(int postId, int userId) throws Exception;

	
	// Delete
	public boolean deletePost(Post post) throws Exception;
}
