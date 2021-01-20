package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.model.Like;
import com.project.service.LikeService;

@Controller
@RequestMapping("/like")
@CrossOrigin("*")
public class LikeController {
	
	private LikeService likeServ;
	
	public LikeController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public LikeController(LikeService likeServ) {
		super();
		this.likeServ = likeServ;
	}


	@GetMapping("/getAllLikes")
	public ResponseEntity<List<Like>> getAllLikes(){
		
		List<Like> allList = likeServ.getAllLikes();
		
		if (allList !=null && allList.size() > 0) {
			return new ResponseEntity<List<Like>>(allList, HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<List<Like>>(allList, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/addLike")
	public ResponseEntity<String> addLike(@RequestBody Like like){
		
		if(like.getUserId() != null && like.getPost() != null) {
			likeServ.addNewLike(like);
			return new ResponseEntity<String>("true", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("false", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/deleteLike")
	public ResponseEntity<String> deleteLike(@RequestBody Like like){
		
		System.out.println(like.getPost());
		System.out.println(like.getUserId());
		
		if(like.getPost() != null && like.getUserId()!=null) {
			
			Like newLike = likeServ.getSpecificLike(like.getPost().getPostId(), like.getUserId().getUserId());
			if(newLike != null) {
				likeServ.deleteLike(newLike);
				return new ResponseEntity<String>("true", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<String>("false", HttpStatus.BAD_REQUEST);
			}
			
		}else {
			return new ResponseEntity<String>("false", HttpStatus.BAD_REQUEST);
		}
		
	}

}
