import React, { useEffect, useState } from 'react';
import './App.css';
import Feed from './components/Feed';
import Header from './components/Header';
import PostForm from './components/PostForm';
import UserPage from './components/UserPage';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import LogInForm from './components/LoginForm';
// import Post from './components/Post';
import RegistrationForm from './components/RegistrationForm';
import axios from 'axios';
import ChangePasswordForm from './components/ChangePasswordForm';
import InvalidTokenModal from './components/InvalidTokenModal';
import { useDispatch, useSelector } from 'react-redux';
import { setAllLikes, setAllUsers, setPosts } from './Redux/action';

function App() {
  //REDUX STUFF
  const user = useSelector(state => state.UserReducer);
  const dispatch = useDispatch();
  const isNewUser = useSelector(state => state.NewUserReducer);
  const userArray = useSelector(state => state.AllUserReducer);
  const otherUser = useSelector(state => state.OtherUserReducer);
  const allPosts = useSelector(state => state.SetPostsReducer);
  const isPostCreated = useSelector(state => state.IsPostCreatedReducer);
  // const postLikes = useSelector(state => state.PostLikesReducer);

  //using useEffect to grab all users and put them in an array
  //upon application loading for search purposes
  useEffect(()=>{
    axios
    .get('http://localhost:9001/Project2/api/user/getAllUsers')
    .then((response)=>{
      dispatch(setAllUsers(response.data));
    })
    .catch(console.error);

    axios.get('http://localhost:9001/Project2/api/post/getAllPosts')
      .then(
        (response) => {
          dispatch(setPosts(response.data));
        }
      )
      .catch((err) => {
        console.error(err);
      });

      //getting all likes
      axios.get('http://localhost:9001/Project2/api/like/getAllLikes')
        .then((response) => {
          dispatch(setAllLikes(response.data));
        })
        .catch((err)=>{
          console.log(err);
        });

  },[user, isPostCreated]);

  return (
    <Router>
      <Header allUsers={userArray}/>
      <Route exact path="/" render={()=> isNewUser === true ? <RegistrationForm /> : <LogInForm />}/>
      <Route path="/user" render={()=><UserPage user={user} canChange={true} />}/>
      <Route path="/otherUser" render={()=><UserPage user={otherUser} canChange={user.username===otherUser.username}/>}/>
      <Route path="/changePassword" component={ChangePasswordForm}/>
      <Route path="/invalidToken" component={InvalidTokenModal}/>
      <Route path="/feed" render={()=>  <Feed posts={allPosts}/>}/>


    </Router>
  );
}

export default App;
