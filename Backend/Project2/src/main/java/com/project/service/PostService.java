package com.project.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.project.model.Post;

public interface PostService {
	public boolean addPost(Post post);
	public List<Post> getAllPosts();
	public Post getPostByID(int postId);
	public List<Post> getAllPostsByUser(int userId);
	public boolean updateText(int postId, String postText);
	public boolean updatePicture(int postId, String picture);
	public boolean updateLikes(int postId, int userId);
	public boolean removePost(Post post);
}
