package com.project.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.model.PasswordResetToken;
import com.project.model.User;

@Repository("resetDAO")
@Transactional
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAO {
	
	private SessionFactory sesFact;
	
	public PasswordResetTokenDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public PasswordResetTokenDAOImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	@Override
	public void addTokenToDB(PasswordResetToken token) {

		Session ses = sesFact.getCurrentSession();
		
		ses.save(token);
		
	}

	@Override
	public void deleteExpiredTokensFromDB(Date now) {

		Session ses = sesFact.getCurrentSession();
		
		CriteriaBuilder cb = ses.getCriteriaBuilder();
		
		CriteriaDelete<PasswordResetToken> query = cb.createCriteriaDelete(PasswordResetToken.class);
		
		Root<PasswordResetToken> root = query.from(PasswordResetToken.class);
		
		query.where(cb.lessThan(root.get("expiryDate"), now));
		
		ses.createQuery(query).executeUpdate();
		
	}

	@Override
	public PasswordResetToken findTokenFromDB(String token) {

		Session ses = sesFact.getCurrentSession();

		CriteriaBuilder cb = ses.getCriteriaBuilder();
		CriteriaQuery<PasswordResetToken> cr = cb.createQuery(PasswordResetToken.class);

		Root<PasswordResetToken> root = cr.from(PasswordResetToken.class);

		cr.select(root).where(cb.equal(root.get("token"), token));

		Query<PasswordResetToken> query = ses.createQuery(cr);
		List<PasswordResetToken> results = query.getResultList();

		if (results.size() == 0) {
			return null;
		}

		return results.get(0);
		
		
	}

}
