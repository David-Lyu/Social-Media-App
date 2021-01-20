package com.project.service;

import java.util.List;

import com.project.model.Like;

/**
 * 
 * @author jwsha
 * 
 * This is the LikeService interface that we'll use to define abstract methods to be implemented in
 * the LikeServiceImpl class
 *
 */
public interface LikeService {
	
	/**
	 * This method will be responsible for adding a new like to the DB (via a transitive call to the method that exists
	 * inside the LikeDAO interface)
	 * 
	 * See the implementation class for further details
	 * 
	 * @param like - this is the like object that we will be adding to the DB
	 */
	public void addNewLike(Like like);
	public List<Like> getAllLikes();
	public Like getSpecificLike(int postId, int userId);
	public void deleteLike(Like like);
	
	
}
