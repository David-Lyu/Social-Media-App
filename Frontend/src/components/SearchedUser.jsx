import React from 'react';

function SearchedUser(props) {
  const searchedUser = props.searchedUser;

  return (
    <div onClick={(e) => searchedUser ? props.setSearchUser(searchedUser.username) : -1} searchedUser={searchedUser} className="d-flex justify-content-start align-items-center">
      {searchedUser ? (
        <>
          <img src={searchedUser.profilePicture ? searchedUser.profilePicture : window.location.origin + "/profile-picture.png"} alt="profile" />
          <div className="mx-3 text-truncate">{searchedUser.username}</div>
        </>
      ) : "No Username found"}
    </div>
  );
}

export default SearchedUser;
