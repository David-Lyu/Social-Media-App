import React, { useState } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { newUser } from '../Redux/action/index'

function RegistrationForm(props) {
    const [isPassSame, setIsPassSame] = useState(true);
    const [isUserNameTaken, setIsUserNameTaken] = useState(false);
    const [hasErrorOccured, setHasErrorOccured] = useState(false);
    const dispatcher = useDispatch();
    /**
     *  this method handles the submit request in creating a new user
     *  parameter:
     *      e: the event that triggered for the submit
     */
    const handleSubmitClick = (e) => {
        e.preventDefault();
        const { email, username, password, confirmPassword, firstName, lastName } = e.target.elements;
        if (password.value !== confirmPassword.value) {
            setIsPassSame(false);
            return;
        }
        setIsPassSame(true);

        const payload = {
            email: email.value,
            username: username.value,
            password: password.value,
            firstName: firstName.value,
            lastName: lastName.value
        }
            axios.post('http://localhost:9001/Project2/api/user/newUser', payload)
                .then(function (response) {
                    if (response.status === 201) {
                        /* setNewUser(false); */
                        dispatcher(newUser(false));
                    }
                })
                .catch(function (error) {
                    console.error(error);
                    if (!error.status || error.response.status !== 409) {
                        setHasErrorOccured(true);
                    } else{
                        setIsUserNameTaken(true);
                    }
                });
    }

    return (
        <div className="container">
            <div className="row justify-content-center align-items-center" id="login-form">
                <form id="form" onSubmit={handleSubmitClick} className="col-md-4 col-sm-4 col-xs-12 content-reg mt-3">
                        <h4>Register a New Account</h4>
                        <div className="form-group">
                            <label className="text-start" htmlFor="exampleInputEmail1">Email Address:</label>
                            <input type="email" required className="form-control" id="email" aria-describedby="emailHelp"
                                placeholder="Email Address" name="email" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="exampleInputUsername1">Username:</label>
                            <input type="username" required className="form-control" id="username"
                                placeholder="Username" name="username" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1">Password:</label>
                            <input type="password" required className="form-control" id="password"
                                placeholder="Password" name="password" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1">Confirm Password:</label>
                            <input type="password" required className="form-control" id="confirmPassword" placeholder="Confirm Password" name="confirmPassword" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="firstName">Enter First Name:</label>
                            <input type="text" required className="form-control" id="firstName" placeholder="Enter First Name" name="firstName" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="lastName">Enter Last Name:</label>
                            <input type="text" required className="form-control" id="lastName" placeholder="Enter Last Name" name="lastName" />
                        </div>

                        <button type="submit" className="btn">Register an Account</button>

                        <button className="btn" onClick={(e) => {
                            e.preventDefault();
                            /* setNewUser(false) */

                            dispatcher(newUser(false));
                        }}>Already a User?</button>
                    </form>
                    {/* error handling here */}
                    {
                        isPassSame ? null : (
                            <div className="text-danger d-flex justify-content-center">
                                Password is not the same!
                            </div>
                        )
                    }
                    {
                        isUserNameTaken ? (
                            <div className="text-danger d-flex justify-content-center">
                                Username is Taken! Please try a different name.
                            </div>
                        ) : null
                    }
                    {
                        hasErrorOccured ? (
                            <div className="text-danger d-flex justify-content-center">
                                An error has occured. Please try again.
                            </div>
                        ) : null
                    }
                </div>
            </div>

    );
}

export default RegistrationForm;
