import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Feed from './Feed';
import PostForm from './PostForm';
import Modal from './Modal';
import { useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setUserPicture } from '../Redux/action';



function UserPage(props) {
    const user = props.user;
    const [isChangingInfo, setIsChangingInfo] = useState(false);
    const [isPictureIconClicked, setIsPictureIconClicked] = useState(false);
    const [srcSetter, setSrcSetter] = useState({src: user.profilePicture, time:Date.now()})
    const dispatch = useDispatch();
    const posts = useSelector(state => state.SetPostsReducer);
    const usersPosts = posts.filter(post => post.author.userId === user.userId);
    let history = useHistory();
    //for handling the click of the modify information button
    const handleChangeClick = (e) => {
        e.preventDefault();
        setIsChangingInfo(!isChangingInfo);
    }

    useEffect(()=> {
        if(!user) {
            return history.push("/");
        }
    })
    return (
        <div className="container">
            <Modal show={isChangingInfo} modalClosed={handleChangeClick}>
                <h3>Modify Your Credentials</h3>
                <p>You need not change everything unless you desire it; change of password requires email confirmation.</p>
                <form onSubmit={changeSubmitHandler(props.user, history)}>
                    <div className="form-group">
                        <label htmlFor="emailInput">New Email</label>
                        <input type="email" className="form-control" id="emailInput" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="usernameInput">New Username</label>
                        <input className="form-control" type="text" id="usernameInput" />
                    </div>
                    <button type="submit" className="btn btn-primary">Submit Changes</button>
                    <div className="d-flex justify-content-center">
                        <a href="/" onClick={(e) => passChangeHandler(e, props.user, history)}>Change your password</a>
                    </div>
                </form>
            </Modal>
            <Modal show={isPictureIconClicked} modalClosed={() => handlePicIconModal(setIsPictureIconClicked)}>
                <form onSubmit={handleProfilePicOnSubmit(setSrcSetter,user,dispatch)}>
                    <label>
                        Please input your profile picture
                        <input type="file" name="file" />
                    </label>
                    <button name="button">Submit</button>
                </form>
            </Modal>
            <div className="container mt-5">
                <div className="row">
                    <div className="col-sm-3 border border-dark card position-relative">
                        {props.canChange ? (
                        <svg onClick={(e) => handleProfileIconOnClick(e, setIsPictureIconClicked)} xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square position-absolute change-profile-photo" viewBox="0 0 16 16">
                            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
                        </svg>
                        ) : null }
                        <img src={user.profilePicture && srcSetter? srcSetter.src +"?" + srcSetter.time: window.location.origin + "/profile-picture.png"} className="card-img-top p-0 img-fluid " alt="Profile" />
                    </div>
                    <div className="mx-3 col d-flex flex-column justify-content-evenly">
                        <div>Username: {user.username}</div>
                        <div>Name: {user.firstName + " " + user.lastName}</div>
                        <div>Email: {user.email}</div>
                        {props.canChange ? <button className="btn btn-primary" onClick={handleChangeClick}>Modify your information.</button> : null}
                    </div>
                </div>
            </div>
            {props.canChange ? <PostForm user={user} /> : null}
            <Feed posts={usersPosts}/>
        </div>
    );
}

/**
 *  passChangeHandler is called when the user clicks the a tag to reset password. It will log the user out
 */
const passChangeHandler = (e, user, history) => {
    e.preventDefault();

    axios.post('http://localhost:9001/Project2/api/user/resetPassword?email=' + user.email)
        .then((response => {
            alert(response.data.message);
            history.push("/");
        }))
        .catch((err) => {
            console.log(err);
            alert("Unable to generate token. Please try again later.");
        })
}

/**
 *  changeSubmitHandler is called when the user submits the form and wants to change an option
 *      the input tags can be left empty and the backend will parse and update accordingly
 *  parameters:
 *      user: current user that you want to change
 *      history: useHistory hook from react router
 */
const changeSubmitHandler = (user, history) => {
    return async function (e) {
        e.preventDefault();

        let newEmail = e.target[0].value;
        let newUsername = e.target[1].value;

        await axios.put('http://localhost:9001/Project2/api/user/updateUser', {
            userId: user.userId,
            username: newUsername,
            email: newEmail
        })
            .then((response) => {
                //reroute to the login page; we want to force the user to login again after changing details
                history.push('/');
            }).catch(console.error);


    }

}

/**
 *  handleProfileIconOnClick is called when the user clicks the pen icon on the top right of
 *      the profile picture. Once clicked it will allow the user to see a modal with
 *      a form to change the picture
 *  parameter:
 *      e: the event from that was triggered from the click
 *      isPicIconClicked: the hook useState that is boolean to show the modal
 */
const handleProfileIconOnClick = (e, isPicIconClicked) => {
    isPicIconClicked(true);
}

/**
 *  handlePicIconModal is called when you click the backdrop or the dark area of the modal
 *      and allows the modal to become hidden
 *  parameters:
 *      isPicIconModalClick: resets the modal and allows for the user to exit the modal
 */
const handlePicIconModal = (isPicIconModalClicked) => {
    isPicIconModalClicked(false);
}

/**
 *  handleProfilePicOnSubmit is called when the user wants to update their profile picture
 *  parameters:
 *      setSrcSetter: this hook receives the url and the time it was sent to render the picture dynamically
 *      user: the current user
 *      dispatch: from react redux's useDispatcher hook
 */
const handleProfilePicOnSubmit = (setSrcSetter,user, dispatch) => {
    return async function(e) {
        e.preventDefault();
        const { button } = e.currentTarget.elements;
        const formData = new FormData(e.currentTarget);
        const init = {
            method: "POST",
            url: `http://localhost:9001/Project2/api/picture?from=user&userId=${user.userId}`,
            headers: {
                "Content-Type": "multipart/form-data"
            },
            data: formData
        }
        button.disabled = true;
        await axios(init)
            .then(resp => {
                button.disabled = false;
                dispatch(setUserPicture(resp.data));
                setSrcSetter({ src: resp.data, time: Date.now() })
            })
            .catch(err => {
                console.error(err);
                button.disabled = false;
            })
    }
}
export default UserPage;
