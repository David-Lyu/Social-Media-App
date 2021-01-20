package com.project.model;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="Posts")

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private int postId;
	
	//length and size sets the string length to a varchar(length)  
	@Column(name="post_text", nullable=false, length=280)
	@Size(min=1, max=280)
	private String postText;
	
	@Column(name="post_picture")
	private String postPicture;
	
	@CreationTimestamp
	@Column(name="timestamp", nullable=false)
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="user_id_fk", referencedColumnName="user_id")
	private User author;
	
	//no arg constructor, all arg constructor,
	//getters/setters & toString()
	
	public Post() 
	{
		//Required for Hibernate.
	}

	public Post(int postId, User user, String postText, String postPicture, LocalDateTime timestamp) 
	{

		super();
		this.postId = postId;
		this.author = user;
		this.postText = postText;
		this.postPicture = postPicture;
		this.timestamp = timestamp;
	}
	
	public Post(int postId, User user, String postText, String postPicture) {
		super();
		this.postId = postId;
		this.author = user;
		this.postText = postText;
		this.postPicture = postPicture;
	}

	public Post(User author, String postText, String postPicture) {
		super();
		this.author = author;
		this.postText = postText;
		this.postPicture = postPicture;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public User getAuthor() {
		return  author;
	}

	public void setAuthor( User user) {
		this.author = user;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getPostPicture() {
		return postPicture;
	}

	public void setPostPicture(String postPicture) {
		this.postPicture = postPicture;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() 
	{
		return "Post [postId=" + postId + ", postText=" + postText + ", postPicture=" + postPicture + ", timestamp="
				+ timestamp + ", author=" + author + "]";

	}

	
}
