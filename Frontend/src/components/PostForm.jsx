import axios from 'axios';
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { isPostCreated } from '../Redux/action';

function PostForm(props) {
    /*
     * USE THIS HOOK TO SET THE IMAGE
     * If no image is given, make it <img src="" alt="">
     * Otherwise make it <img src="IMAGE_URL_HERE" alt="">
     *
     * ADD AN ON-CLICK EVENT LISTENER TO THE IMAGE
     * When the user clicks the upload file image, ask them for an image url
     * And set the img const below to that image url
     *
     * ALSO ADD AN ON-CLICK EVENT LISTNER TO THE USERNAME
     * When the user clicks on a username, take them to that user's page
     * Do the same thing in the posts component so any username works
     * [See the posts like button for an example on event listeners]
     *
     * ALSO ADD AN ON-HOVER EVENT LISTENER TO THE USERNAME
     * When the user mouses over a username, have it become underlined
     *
     * ALSO ADD AN ON-CLICK EVENT LISTENER TO THE LEFT HEADER TEXT
     * When a user clicks on the "Project2 - Air Nomads" text,
     * take them back to the page that displays the post creation/feed
     *
     * FOR THE SEARCH BAR
     * If a user searches for a user and they exist, display their user page
     * but if the user does not exist, stay on the same page
     *
     * TO ALLOW THE USER UPLOAD MULTIPLE IMAGES
     * Create a new image tag for every image and insert it
     */
    const [imageExists, setPicture] = useState(false);
    const [isSent, setIsSent] = useState(false);
    const dispatch = useDispatch();
    const image = 'https://images-ext-2.discordapp.net/external/7q7IKmvlGqtofDERdSuD__vUPewQ6mQg2T2cDuDu0bc/https/project2-profile-pic.s3.us-east-2.amazonaws.com/try2.png';
    return (
        <div className="container" id="container">
            <form id="postCreation" onSubmit={e => handlePostSubmitClick(e, props.user, setIsSent,setPicture, dispatch)}>
                <div id="postCreationFlex">
                    <div id="postCreationImage">
                        <img src={props.user ? props.user.profilePicture : window.location.origin + "/profile-picture.png"} alt="profile" id="postCreationPicture" />
                    </div>
                    <textarea minLength="5" type="text" id="postCreationText" name="postText" placeholder={props.user && props.user.username ? `What's on your mind, ${props.user.firstName[0].toUpperCase() + props.user.firstName.substring(1, props.user.firstName.length)} ?` : null} />
                    <label className="position-relative upload-post-picture">
                        <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor" className="bi bi-file-earmark-arrow-up" viewBox="0 0 16 16">
                            <path d="M8.5 11.5a.5.5 0 0 1-1 0V7.707L6.354 8.854a.5.5 0 1 1-.708-.708l2-2a.5.5 0 0 1 .708 0l2 2a.5.5 0 0 1-.708.708L8.5 7.707V11.5z" />
                            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
                        </svg>
                        <input type="file" name="file" className="position-absolute" />
                    </label>
                </div>
                {/* Conditionally render in the uploaded image */}
                {imageExists ? <div id="postCreationAddedImage">
                    <img src={image} alt="" />
                </div> : null}
                <input disabled={isSent} type="submit" value="Submit" id="submit" className="btn" />
            </form>
        </div>
    );
}

/**
 *  handlePostSubmitClick is called when the user submits the form with all the entries
 *      and sends the data back to the backend
 *  parameter:
 *      e: the event that occured on the submit
 *      user: the user "author" of the post
 *      setIsSent: a hook that is in redux that lets the pages know that the post was sent
 *      setPicture: allows the picture to change (no implementations with this parameter)
 *      dispatch: react redux's dispatcher hook
 */
function handlePostSubmitClick(e,user,setIsSent, setPicture, dispatch) {
    e.preventDefault();
    setIsSent(true);
    let {postText, file} = e.currentTarget.elements
    if(user) {
        const formData = new FormData(e.currentTarget);
        const init = {
            method: "POST",
            url: `http://localhost:9001/Project2/api/picture?from=post&userId=${user.userId}`,
            headers: {
                "content-type": "multipart/form-data"
            },
            data: formData
        }
        axios(init)
        .then(resp => {
            dispatch(isPostCreated());
            file.value = "";
            postText.value = "";
        })
        .catch(err => {
            console.error(err);
        })
        .finally(()=> {
            setIsSent(false);
            dispatch(isPostCreated());
        })
    }
}
export default PostForm
