package com.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.project.hasher.PBDKF2Hasher;
import com.project.model.Post;
import com.project.model.User;
import com.project.service.PostService;
import com.project.service.UserService;

@RequestMapping("picture")
@Controller
@CrossOrigin(origins = "*")
public class PictureController {

	private UserService usi;
	private PostService psi;
	
	

	@Autowired
	public PictureController(UserService usi, PostService psi) {
		super();
		this.usi = usi;
		this.psi = psi;
	}

	/**
	 * This method gets the picture in multip-part form data and parses into a
	 * InputStream cannot get parameters tradtionally because of the content-type.
	 * Would have to go through each item from fileItems to get the content. There
	 * should only be one parameter here. Code was retrieved from http://commons.apache.org/proper/commons-fileupload/using.html
	 * and modified to not store the file directly
	 * 
	 * @param req HttpServletRequest. Not spring
	 * @param res HttpServletResponse. Not spring
	 * @return a status
	 */
	///return url
	//// Maybe send a request in the params to be either ?from={user|post}&id=userid
	//// body data includes attributes of file , password, postText
	@PostMapping
	public @ResponseBody ResponseEntity<String> getPicture(HttpServletRequest req, HttpServletResponse res) {
		int id = -1;
		String from = req.getParameter("from");
		PBDKF2Hasher hasher = new PBDKF2Hasher();
		User user = null;
		try{
			id = Integer.parseInt(req.getParameter("userId"));
			user = (User) usi.getUserByID(id);
			if(user == null) throw new NumberFormatException();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("false", HttpStatus.BAD_REQUEST);
		}
		String websiteLocation = req.getRequestURI();
		String s3Url = null;
		String fileName = user.getUsername();
		Map<String, String> otherParams = new HashMap<>();
		//checks if content-type is multipart/form-data
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) return new ResponseEntity<String>("false", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("false", HttpStatus.CONFLICT);
		}
		//first round of looking at body
		for(int i = 0; i < items.size(); i++) {
			FileItem item = items.get(i);
			///checks the credentials here
			if(item.getFieldName().equals("password")) {
				String password = item.getString();
				if(!user.getPassword().equals(password)) {
					return new ResponseEntity<String>("wrong password", HttpStatus.UNAUTHORIZED);
				} 
			}
			//checks if the request was from post or user
			if(from.equals("post")) {
				if(item.getFieldName().equals("postText")) {
					fileName += item.getString().substring(0, 5);
				}
			}
		}
		
		// goes through items list and checks content type and sends it to s3 accordingly and stores non file types to the list
		for (int i = 0; i < items.size(); i++) {
			FileItem item = items.get(i);
			if (item.getContentType() != null) {
				s3Url = sendToS3(item,fileName);
			} else {
				otherParams.put(item.getFieldName(),item.getString());
			}
		}
		otherParams.put("picture", s3Url);
		// calls appropriate method to update user or create post
		if (from.equals("user")) {
			// user logic
				changeUserProfilePic(user,otherParams);
		} else if (from.equals("post")) {
			// post logic
			System.out.println("inside post logic");
				createNewPost(user,otherParams);
		} else {
			return new ResponseEntity<String>("no user param inserted", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(s3Url, HttpStatus.CREATED);
	}

	/**
	 * This method is called when there is a user in the uri and will call the User service layer
	 * 
	 * @param items
	 * @param req
	 * @param res
	 */
	public void changeUserProfilePic(User user, Map<String, String> otherParams) {
		// insert user service and then update users
		user.setProfilePicture(otherParams.get("picture"));
		usi.updatePicture(user.getUserId(), otherParams.get("picture"));
	}

	/**
	 * This method is called when there is a post in the uri and will call the Post service layer
	 * 
	 * @param items
	 * @param req
	 * @param res
	 */
	public void createNewPost(User user, Map<String, String> otherParams) {
		System.out.println(otherParams.get("picture"));
		Post post = new Post(user, otherParams.get("postText"),otherParams.get("picture"));
		System.out.println(post);
		psi.addPost(post);
	}

	/**
	 * This method sends the picture into the s3 bucket and returns the string of
	 * the url. The keys are set in the env named : AWS_ACCESS_KEY amd
	 * AWS_SECRETE_ACCESS_KEY. Code was retreived from https://docs.aws.amazon.com/AmazonS3/latest/dev/UploadObjSingleOpJava.html
	 * and modified to use FileItems instead of File
	 * 
	 * @param fileItem: from getPicture method above which should be the file being
	 *                  sent.
	 */
	public String sendToS3(FileItem fileItem, String fileName) {
		Regions clientRegion = Regions.US_EAST_2;
		String bucketName = "project2-profile-pic";
		String url = null;
		int indexToFileType = fileItem.getName().lastIndexOf('.');
		if (indexToFileType == -1)
			return url;
		String fileType = fileItem.getName().substring(indexToFileType).toLowerCase();
		////////////////////// grab a sequence from the database?
		String fileObjKeyName = fileName + fileType;
		InputStream fileIO = null;

		try {
			fileIO = fileItem.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			BasicAWSCredentials awsCred = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY"),
					System.getenv("AWS_SECRET_ACCESS_KEY"));
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
					.withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();

			// Upload a file as a new object with ContentType and title specified.
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("multipart/form-data");
			metadata.addUserMetadata("title", "someTitle");
			PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, fileIO, metadata);
			PutObjectResult putObject = s3Client.putObject(request);
			url = s3Client.getUrl(bucketName, fileObjKeyName).toString();
		} catch (AmazonServiceException e) {
			System.out.println("contact s3 but cant put item inside");
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			System.out.println("no response at all");
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
		return url;
	}
}