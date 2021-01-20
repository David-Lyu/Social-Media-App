package com.project.service;

import java.util.List;

import com.project.model.User;

public interface UserService {
	public boolean addUser(User user);
	public List<User> getAllUsers();
	public User getUserByID(int user_id);
	public User getUserByUsername(String username);
	public User getUserByEmail(String email);
	public boolean updateUsername(int user_id, String username);
	public boolean updateEmail(int user_id, String email);
	public boolean updatePassword(int user_id, String password, String salt);
	public boolean updatePicture(int user_id, String picture);
	public boolean removeUser(User user);
	public boolean verifyCredentials(String username, String password);
	public boolean checkUsernameExists(String username);
	public void createPasswordResetTokenForUser(User user, String token);
}
