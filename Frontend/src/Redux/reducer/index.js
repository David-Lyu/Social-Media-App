import { combineReducers } from 'redux';
import { AllLikesReducer, PostLikesReducer, SetLikeReducer } from './LikeReducer';
import { IsPostCreatedReducer, SetPostsReducer } from './PostReducer';
import { UserReducer, newUserReducer, AllUserReducer, OtherUserReducer } from './UserReducer';

const allReducers = combineReducers({
    UserReducer: UserReducer,
    NewUserReducer: newUserReducer,
    AllUserReducer: AllUserReducer,
    OtherUserReducer: OtherUserReducer,
    AllLikesReducer : AllLikesReducer,
    SetPostsReducer : SetPostsReducer,
    PostLikesReducer : PostLikesReducer,
    IsPostCreatedReducer
})

export default allReducers;
