package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Likes")

public class Like {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private int likeId;
	
	@ManyToOne
	@JoinColumn(name="post_id_fk", referencedColumnName="post_id")
	private Post post;
	
	@OneToOne
	@JoinColumn(name="user_id_fk", referencedColumnName="user_id")
	private User user;
	
	//no arg constructor, all arg constructor
	//getters/setters & toString()
	
	public Like() {
		// TODO Auto-generated constructor stub
	}

	public Like(int likeId, Post post, User user) {
		super();
		this.likeId = likeId;
		this.post = post;
		this.user = user;
	}
	

	public Like(Post post, User user) {
		super();
		this.post = post;
		this.user = user;
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}

	public Post getPost() {
		return post;
	}

	public void setPostId(Post post) {
		this.post = post;
	}

	public User getUserId() {
		return user;
	}

	public void setUserId(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "\n\tLike [likeId=" + likeId + ", post=" + post.getPostId() + ", user=" + user.getUserId() + "]";
	}

}
