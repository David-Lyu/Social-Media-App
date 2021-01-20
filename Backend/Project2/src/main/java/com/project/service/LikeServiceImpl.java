package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.LikeDAO;
import com.project.model.Like;
/**
 * 
 * @author Joe Shannon
 * 
 * This is the concrete implementation class that will implement the abstract methods defined in
 * LikeService.
 * 
 * We use Spring dependency injection as you can see in the autowiring of the constructor
 *
 */
@Service("likeServ")
public class LikeServiceImpl implements LikeService {

	private LikeDAO likeDao;
	
	public LikeServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	//autowired constructor
	@Autowired
	public LikeServiceImpl(LikeDAO likeDao) {
		this.likeDao = likeDao;
	}
	


	/**
	 * As of now, this seems to be the only method we'd need for the Like Service
	 * Corresponds precisely to the only practical CRUD method in LikeDAOImpl
	 * 
	 * @param like - this is the Like object that we will be adding to the DB
	 */
	@Override
	public void addNewLike(Like like) {

		likeDao.addLikeToDB(like);
		
	}


	@Override
	public List<Like> getAllLikes() {
		
		return likeDao.readAllLikes();
	}


	@Override
	public void deleteLike(Like like) {

		likeDao.deleteLike(like);
		
	}


	@Override
	public Like getSpecificLike(int postId, int userId) {

		return likeDao.readSpecificLike(postId, userId);
		
	}

}
