export const AllLikesReducer = (state=[], action) => {
    switch(action.type){
        case "setAllLikes":
            return action.payload;
        default:
            return state;
    }
}

// export const SetLikeReducer = (state=false, action) => {
//     switch(action.type){
//         case "setLike":
//             return !state;
//         default:
//             return state;
//     }
// }

export const PostLikesReducer = (state = [], action) => {
    switch(action.type){
        case "setPostLikes":
            return action.payload;
        default:
            return state;
    }
}