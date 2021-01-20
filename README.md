# Social Media App

## Overview:
 - A full stack web application that allows user to create an account and share posts as well as view other people's profile and like their posts.

## Technologies:

 #### Backend:
 - Apache Tomcat 9.0
 - Apache Commons
 - Amazon SDK
 - Amazon AWS
 - Bootstrap 4
 - Jackson Databind
 - Jackson Core
 - Javax mail
 - Log4j API 1.2.17
 - Spring
 - Spring ORM
 - Spring MVC

 ### Frontend:
 - axios 
 - Nodejs 14.15
 - React 17.0.1
 - React Redux 4.0.5
 - React Router 5.2.0

 ### Testing:
 - JUnit
 - Jest

## Features
 - User can create an account
 - User can Login 
 - User can view all posts by everyone
 - User can view their own account
 - User can view other accounts
 - User can change their settings
 - User can create a posts
 - User can like a posts
 - User can search for other Users

## Getting Started/Usage

In order to see this project in action, you will need a few things:
  
  ###### Backend 
  1) Be sure to have an AWS account with S3 bucket enabled
   - Have an environment variables with "AWS_ACCESS_KEY" & "AWS_SECRET_ACCESS_KEY"
  2) Be sure to have a gmail account for the server to send email for password updating
   - Have an environment variable with "GMAIL_USERNAME" & "GMAIL_PASSWORD"
  3) Be sure to have Apache Tomcat 9.0 installed
  4) Be sure to have Java 8 run time environment installed

  ###### Frontend
  1) npm install

If all the pre-requisites above are met, go ahead and clone this repo by using the below command:

        GIT URL

Once clone, copy the .war file located within the /target directory and paste it into your tomcat webapps folder.

Once the .war has been uploaded, by default you will be able to view the application with "npm start" in the command line inside the Frontend directory.
 - Tomcat server should listening on http://localhost:9001/

## Contributors
 - [Albert Washington](https://gitlab.com/A-washi)
 - [David Lyu](https://github.com/David-Lyu)
 - [Joeseph Shannon](https://gitlab.com/jshannon77)
 - [Sean Reihl](https://gitlab.com/seankriehl)
