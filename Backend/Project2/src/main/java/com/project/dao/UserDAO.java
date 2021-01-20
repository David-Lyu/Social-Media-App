package com.project.dao;

import java.util.List;

import com.project.model.User;

public interface UserDAO {
	// Create
	public boolean insertUser(User user);

	// Read
	public List<User> readAllUsers();
	public User readUserByID(int user_id);
	public User readUserByUsername(String username);
	public User readUserByEmail(String email);

	// Update
	public boolean updateUsername(int user_id, String username);
	public boolean updatePassword(int user_id, String password, String salt);
	public boolean updateEmail(int user_id, String email);
	public boolean updatePicture(int user_id, String picture);

	// Delete
	public boolean deleteUser(User user);
	
	//Testing methods
	public void h2InitDao();
	public void h2DestroyDao();
}
