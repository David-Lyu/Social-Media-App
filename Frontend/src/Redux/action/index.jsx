export const setUser = (user) => {
  return {
    type: "setUser",
    payload: user
  }
}

export const setUserPicture = picture => {
  return {
    type: "setUserPicture",
    payload: picture
  }
}

export const newUser = (value) => {
  return{
    type: "setNewUser",
    payload: value
  }
}

export const setAllUsers = (userArray) => {
  return {
    type: "setAllUsers",
    payload: userArray
  }
}

export const setOtherUser = (otherUser) => {
  return {
    type: "setOtherUser",
    payload: otherUser
  }
}

export const setPosts = posts => {
  return {
    type: "setPosts",
    payload: posts
  }
}

export const setAllLikes = likes => {
  return {
    type: "setAllLikes",
    payload: likes
  }
}

// export const setLike = () => {
//   return {
//     type: "setLike"
//   }
// }

export const setPostLikes = (likes) => {
  return {
    type: "setPostLikes",
    payload: likes
  }
}

export const isPostCreated = post => {
  return {
    type: "isPostCreated"
  }
}
