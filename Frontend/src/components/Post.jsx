import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setAllLikes } from '../Redux/action';

function Post(props) {
    const [like, setLike] = useState(false);
    const allLikes = useSelector(state => state.AllLikesReducer);
    const allPostLikes = allLikes.filter((like) => like.post.postId === props.postInfo.postId);
    const [count, setCount] = useState(allPostLikes.length);
    const dispatch = useDispatch();
    const currentUser = useSelector(state => state.UserReducer);

    //when the component mounts if  it filters all the likes and sets it in like variable
    useEffect(() => {
        setLike(props.postLikes.some((like) => (like.userId.userId === currentUser.userId)));
    }, [])
    //when setLike gets changed it will call a axios and get a list of all new likes
    useEffect(() => {
        //getting all likes
        axios.get('http://localhost:9001/Project2/api/like/getAllLikes')
            .then((response) => {
                dispatch(setAllLikes(response.data));
            })
            .catch((err) => {
                console.log(err);
            });
    }, [setLike])

    return (
        <div className="col-lg-4" id="post-div">
            <div className="card" id="post">
                <div className="card-body">
                    <div className="postHeader">
                        <div id="postHeaderInfo">
                            {/*Splash picture of post creator.*/}
                            <img src={props.postInfo && props.postInfo.author.profilePicture ? props.postInfo.author.profilePicture : window.location.origin + "/profile-picture.png"} className="img-thumbnail" alt="..." id="profilePicture" />
                            {/*Displays post Name and timestamp*/}
                            <div id="postInfo">
                                <p id="postAuthor">{props.postInfo ? props.postInfo.author.firstName + ' ' + props.postInfo.author.lastName : 'AUTHOR'}</p>
                                <p id="postTimestamp">{props.postInfo ? props.postInfo.timestamp.month + ' ' + props.postInfo.timestamp.dayOfMonth + ', ' + props.postInfo.timestamp.year : 'TODAY'}</p>
                            </div>
                        </div>
                        <div id="postLikeButton">
                            <span id="count">{count}</span>
                            <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" className={`${like ? `blue` : ``} bi bi-arrow-up-square-fill`} id="svg" viewBox="0 0 16 16" onClick={
                                function () {
                                    setLike(!like)
                                    if (like) {
                                        deleteLike(props.postInfo.postId, currentUser.userId);
                                        setCount(count - 1);
                                    } else {
                                        addLike(props.postInfo.postId, currentUser.userId);
                                        setCount(count + 1);
                                    }
                                }
                            }>
                                <path d="M2 16a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2zm6.5-4.5V5.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 5.707V11.5a.5.5 0 0 0 1 0z" />
                            </svg>
                        </div>
                    </div>
                    <p className="card-text" id="postText">{props.postInfo ? props.postInfo.postText : ''}</p> {/* The ternary is useless here as post text will never be empty. */}
                </div>
                <div id="postPicture">
                    {props.postInfo.postPicture ? <img src={props.postInfo.postPicture} alt="..." /> : <></>}

                </div>
            </div>
        </div>
    );
}

/**
 *  addLike is called when someone clicks the svg icon and like is falsey
 *  parameter:
 *      postId: is the id of the post
 *      userId: is the id of the user who like the post
 */
function addLike(postId, userId) {
    axios.post('http://localhost:9001/Project2/api/like/addLike', {
        "post": {
            "postId": postId
        },
        "userId": {
            "userId": userId
        }
    })
        .then((res) => {
            console.log("hello everybody!");
        })
        .catch((err) => {
            console.log(err);
        })
}

/**
 *  deleteLike is a method called when someone clicks the svg icon and like is truthy
 *  parameter:
 *      postId: id of the post being liked
 *      userId: id of the user liking the post
 */
function deleteLike(postId, userId) {
    axios.post('http://localhost:9001/Project2/api/like/deleteLike', {
        "post": {
            "postId": postId
        },
        "userId": {
            "userId": userId
        }
    })
        .then((res) => {
            console.log("hi dr. nick");
        })
        .catch((err) => {
            console.log(err);
        })

}

export default Post
