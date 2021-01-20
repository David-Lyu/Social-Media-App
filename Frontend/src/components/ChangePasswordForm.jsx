import React from 'react';
import { useHistory } from 'react-router-dom';
import Modal from "./Modal";
import axios from 'axios';

const ChangePasswordForm = (props) => {

    console.log(window.location)
    let history = useHistory();

    return (<div className="container">
        <Modal show={true} modalClosed={null}>
            <h3>Change your Password</h3>
            <form onSubmit={passwordSubmitHandler(history)}>
                <div className="form-group">
                    <label for="emailInput">Enter your email:</label>
                    <input type="email" className="form-control" id="emailInput" />
                </div>
                <div className="form-group">
                    <label for="passwordInput">New Password</label>
                    <input className="form-control" type="password" id="passwordInput" />
                </div>
                <div className="form-group">
                    <label for="confirmInput">Confirm Password</label>
                    <input className="form-control" type="password" id="confirmInput" />
                </div>
                <button type="submit" className="btn btn-primary">Submit Password Change</button>
            </form>
        </Modal>
    </div>);
}
/**
 *  passwordSubmitHandler is a method that is called when the user clicks the button in the form element
 *      to change his password. There should be a query of a token generated from the email of the
 *      user
 *  parameter(s):
 *      history: this is from the react router api and routes us back to the home page.
 */
const passwordSubmitHandler = (history) => {
    return async function (e) {
        e.preventDefault();

        //first we use the user's entered email to retrieve their info
        let email = e.target[0].value;
        let password = e.target[1].value;
        let confirmPassword = e.target[2].value;
        let user;

        if (password === confirmPassword) {
            //axios call to get the user's information
            let url = window.location.href;
            if(url.lastIndexOf("?") === -1)  history.push("/")
            let token = url.substring(url.lastIndexOf('?'), url.length);

            if (url.lastIndexOf("?") !== -1 || token) {
                await axios.get('http://localhost:9001/Project2/api/user/getUserByEmail?email=' + email)
                    .then((response) => {
                        user = response.data
                        user.password = password;

                        //now we reset the user's password
                        axios.put('http://localhost:9001/Project2/api/user/updatePassword', user)
                            .then((res) => {
                                alert("Update was successful. You'll now be redirected to the login page.")
                                history.push("/");
                            })
                            .catch((err) => {
                                console.log(err);
                                alert("Could not update. Try again later.");
                            })

                    })
                    .catch((err) => {
                        console.log(err);
                        alert("No user found with that email.");
                    })

            } else {
                alert('No valid token found.');
            }



        } else {
            alert("Passwords don't match. Try again.");
        }



    }

}

export default ChangePasswordForm;
