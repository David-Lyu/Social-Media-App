export const SetPostsReducer = (state = [], action) => {
  switch (action.type) {
    case "setPosts":
      return action.payload;
    default:
      return state;
  }
}

export const IsPostCreatedReducer = (state = false, action) => {
  switch (action.type) {
    case "isPostCreated":
      return !state;
    default:
      return state;
  }
}
