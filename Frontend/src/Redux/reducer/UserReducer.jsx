

export const UserReducer = (state={}, action) => {
  switch(action.type) {
    case "setUser":
      return action.payload;
    case "setUserPicture":
      return {...state, profilePicture: action.payload}
    default:
      return state
  }
}


export const newUserReducer = (state=true, action) => {
  switch(action.type) {
    case "setNewUser":
      return action.payload;
    default:
      return state
  }
}

export const AllUserReducer = (state = [], action) => {
  switch(action.type) {
    case "setAllUsers":
      return action.payload;
    default:
      return state;
  }
}

export const OtherUserReducer = (state = {}, action) => {
  switch(action.type) {
    case "setOtherUser":
      return action.payload
    default:
      return state;
  }
}
