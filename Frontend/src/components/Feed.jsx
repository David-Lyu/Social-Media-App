import React, { useEffect, useRef } from 'react';
import { useSelector } from 'react-redux';
import Post from './Post';

function Feed(props)
{

    const allLikes = useSelector(state => state.AllLikesReducer);
    let postList = useRef(props.posts);

    useEffect(()=> {
        postList.current = props.posts;
    }, [props])

    let renderPosts = function(postsList)
    {
        if (postsList.length)
        {
            return postList.current.map((post) => {
                const postLikes = allLikes.filter((like) => post.postId === like.post.postId);
                return(<Post key={post.postId} postInfo={post} postLikes={postLikes}/>)
            })
        }
        else
        {
            return <div><br/><br/><h4 className="text-center">No posts to show.</h4><br/><br/></div>
        }
    }

    return( <div className="container">
        <div className="row justify-content-start">

        {renderPosts(props.posts)}

        </div>

    </div>)


}

export default Feed
