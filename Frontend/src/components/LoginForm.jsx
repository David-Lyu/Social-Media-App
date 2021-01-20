import axios from 'axios';
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { newUser, setUser } from '../Redux/action';

function LogInForm(props) {
    const dispatch = useDispatch();
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    let history = useHistory();

    const handleSubmitClick = (e) => {
        e.preventDefault();

        axios.post('http://localhost:9001/Project2/api/user/login', { email: email, password: password })
            .then((response) => {
                dispatch(setUser(response.data));
                if (response.data) {
                    history.push('/user');
                }
            })
            .catch((err) => {
                console.log(err);
                alert("The credentials you entered were invalid. Please try again.");
            });
    }

    return (
        <div className="container">
            <div className="row justify-content-center align-items-center" id="login-form">
                <form id="form" className="col-md-4 col-sm-4 col-xs-12 mt-3" onSubmit={e => {
                    handleSubmitClick(e)
                }}>
                    <h4>Log In</h4>
                    <div className="form-group">
                        <label className="text-start" htmlFor="exampleInputEmail1">Email Address:</label>
                        <input type="email" className="form-control" id="email" aria-describedby="emailHelp"
                            placeholder="Email Address" value={email} onChange={(e) => {
                                setEmail(e.target.value)
                            }} />
                    </div>

                    <div className="form-group">
                        <label htmlFor="exampleInputPassword1">Password:</label>
                        <input type="password" className="form-control" id="password"
                            placeholder="Password" value={password} onChange={(e) => {
                                setPassword(e.target.value)
                            }} />
                    </div>

                    <button type="submit" className="btn">Log In</button>

                    <button className="btn" onClick={(e) => {
                        e.preventDefault()
                        /* props.setNewUser(true) */
                        dispatch(newUser(true));
                    }}>Register an Account</button>
                </form>
            </div>
        </div>
    );
}

export default LogInForm;
