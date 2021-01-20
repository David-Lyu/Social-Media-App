package com.project.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password_salt", nullable = false)
	private String salt;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "profile_picture")
	private String profilePicture;

	// no arg constructor, all arg constructor
	// getters/setters & toString()

	public User() 
	{
		// Hibernate requires an empty constructor
	}


//	public User(int userId, String username, String password, String firstName, String lastName,
//			String email, String profilePicture) {
//
//		super();
//		this.userId = userId;
//		this.username = username;
//		this.password = password;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.profilePicture = profilePicture;
//	}
//
//
//
//	public User(String username, String password, String firstName, String lastName, String email,
//			String profilePicture) {
//
//		super();
//		this.username = username;
//		this.password = password;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.profilePicture = profilePicture;
//	}
	
	public User(int userId, String username, String salt, String password, String firstName, String lastName,
			String email, String profilePicture) {
		super();
		this.userId = userId;
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.profilePicture = profilePicture;
	}

	public User(String username, String salt, String password, String firstName, String lastName, String email,
			String profilePicture) {
		super();
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.profilePicture = profilePicture;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}


	@Override
	public String toString() {

		return "\n\tUser [userId=" + userId + ", username=" + username + ", salt=" + salt + " password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", profilePicture="
				+ profilePicture + "]";

	}

//	@Override
//	public String toString() {
//		return "\n\tUser [userId=" + userId + ", username=" + username + ", salt=" + salt + " password=" + password
//				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", profilePicture="
//				+ Arrays.toString(profilePicture) + "]";
//	}
	
	

}
