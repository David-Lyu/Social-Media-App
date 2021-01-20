package com.project.dao;

import java.util.Date;

import com.project.model.PasswordResetToken;

public interface PasswordResetTokenDAO {
	public void addTokenToDB(PasswordResetToken token);
	public void deleteExpiredTokensFromDB(Date now);
	public PasswordResetToken findTokenFromDB(String token);
}
