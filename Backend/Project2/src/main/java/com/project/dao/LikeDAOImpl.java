package com.project.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.model.Like;
import com.project.model.Post;
import com.project.model.User;

/**
 * 
 * @author Joe Shannon
 * 
 * This class provides the concrete implementation of the abstract methods defined in the LikeDAO interface.
 *
 */
@Repository("likeDao")
@Transactional
public class LikeDAOImpl implements LikeDAO {

	
	private SessionFactory sesFact;
	
	public LikeDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public LikeDAOImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}


	/*
	 * This seems to be the only likeDao method that we would need for this project
	 */
	@Override
	public void addLikeToDB(Like like) {

		sesFact.getCurrentSession().save(like);
		

	}

	@Override
	public List<Like> readAllLikes() {

		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<Like> cr = cb.createQuery(Like.class);

		Root<Like> root = cr.from(Like.class);
		cr.select(root);

		Query<Like> query = ses.createQuery(cr);
		List<Like> results = query.getResultList();

		if (results.size() == 0) {
			return null;
		}

		return results;
	}

	@Override
	public List<Like> readLikeByUser(int userId) {

//		//Session ses = HibernateUtil.getSession();
//
//		CriteriaBuilder cb = ses.getCriteriaBuilder();
//		CriteriaQuery<Like> cr = cb.createQuery(Like.class);
//
//		Root<Like> root = cr.from(Like.class);
//
//		cr.select(root).where(cb.equal(root.get("user"), userId));
//		
//		Query<Like> query = ses.createQuery(cr);
//		List<Like> results = query.getResultList();
//
//		// Boilerplate session end
//		ses.close();
//
//		if (results.size() == 0) {
//			return null;
//		}
//
		return null;
	}

	@Override
	public List<Like> readLikeByPost(int postId) {
//		//Session ses = HibernateUtil.getSession();
//
//		CriteriaBuilder cb = ses.getCriteriaBuilder();
//		CriteriaQuery<Like> cr = cb.createQuery(Like.class);
//
//		Root<Like> root = cr.from(Like.class);
//
//		cr.select(root).where(cb.equal(root.get("post"), postId));
		return null;
	}

	@Override
	public void deleteLike(Like like) 
	{
		Session ses = sesFact.getCurrentSession();
		
		
		ses.delete(like);
		
		//Possibly return success.

	}

	@Override
	public Like readSpecificLike(int postId, int userId) {

		Session ses = sesFact.getCurrentSession();
		
		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<Like> cr = cb.createQuery(Like.class);
		
		Root<Like> root = cr.from(Like.class);
		Predicate predicateForPost = cb.equal(root.get("post"), postId);
		Predicate predicateForUser = cb.equal(root.get("user"), userId);
		
		Predicate finalPredicate = cb.and(predicateForPost, predicateForUser);
		cr.where(finalPredicate);
		Query<Like> query = ses.createQuery(cr);
		List<Like> likeList = query.getResultList();
		
		if(likeList.size()>0) {
			return likeList.get(0);
		}
		
		return null;
	}

}
