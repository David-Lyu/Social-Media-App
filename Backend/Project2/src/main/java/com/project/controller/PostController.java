package com.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.model.Post;
import com.project.model.User;
import com.project.service.PostService;
import com.project.service.UserService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/post")
public class PostController {
	
	private UserService usi;
	private PostService psi;
	
	
	public PostController() 
	{
		
	}

	@Autowired
	public PostController(UserService usi, PostService psi) {
		this.usi = usi;
		this.psi = psi;
	}

	public UserService getUsi() {
		return usi;
	}


	public void setUsi(UserService usi) {
		this.usi = usi;
	}


	public PostService getPsi() {
		return psi;
	}


	public void setPsi(PostService psi) {
		this.psi = psi;
	}


	@GetMapping("/getAllPosts")
	public List<Post> getAllPost() throws JsonProcessingException, IOException 
	{
		return psi.getAllPosts();
		
		
	}
	
	@GetMapping("/getUserPosts")
	public List<Post> getAllUserPosts(@RequestBody User user) throws JsonProcessingException, IOException
	{
		
		return psi.getAllPostsByUser(user.getUserId());
		
	}
	
	@GetMapping("/getPost")
	public Post getPostbyId(@RequestBody Post post) throws JsonProcessingException, IOException
	{
		
		return psi.getPostByID(post.getPostId());
		
	}
}
