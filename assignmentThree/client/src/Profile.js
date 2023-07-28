import React, {Fragment, useState} from "react";
import {useNavigate} from "react-router-dom";
import styles from "./login.module.css";
import {useLocation} from "react-router-dom";
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Profile() {

    const location = useLocation();

    let user = JSON.parse(location.state.uid)


    console.log(user);

    const [id, setId] = useState(user._id)
    const [email, setEmail] = useState(user.email.toString().replace(/\"/g, ""));
    const [name, setName] = useState(user.name.toString().replace(/\"/g, ""));
    const [userArray, setUserArray] = useState([]);

    console.log(id);

    let navigate = useNavigate();

    const updateUser = () => {

        let item = {"name": name, "email": email}

        let result = fetch("http://localhost:3005/user/" + id, {
            method: "PUT", headers: {
                "env": "test", "content-type": "application/json"
            }, body: JSON.stringify(item)
        }).then(response => response.text())
            .then(result => {
                console.log(result)
                toast.success('Profile Updated', {
                    position: toast.POSITION.BOTTOM_CENTER
                });
            })
            .catch(error => console.log('error', error));
    };

    const deleteCurrentUser = () => {

        let result = fetch("http://localhost:3005/user/" + id, {
            method: "DELETE", headers: {
                "env": "test", "content-type": "application/json"
            }
        }).then(response => response.text())
            .then(result => {
                console.log(result)
                navigate(-1)
                toast.success('Profile Deleted', {
                    position: toast.POSITION.BOTTOM_CENTER
                });
            })
            .catch(error => console.log('error', error));
    };

    const displayAll = () => {

        let result = fetch("http://localhost:3005/", {
            method: "GET", headers: {
                "env": "test", "content-type": "application/json"
            }
        }).then((response) => response.json())
            .then((result) => {
                console.log(result)
                let array = [];
                for (let t of result) {
                    array.push(
                        <div>
                            <p>{"Name: " + t.name + " -> Email:" + t.email}</p>
                        </div>
                    )
                }
                setUserArray(array);
            })
            .catch(error => console.log('error', error));
    };

    return (<div className={styles.login}>
        <input className={styles.input} value={email} onInput={e => setEmail(e.target.value)} type="email"
               placeholder="Email"/>
        <input className={styles.input} value={name} onInput={e => setName(e.target.value)} type="name"
               placeholder="Name"/>
        <button onClick={() => {
            updateUser();
        }} className={styles.signin_button}>Update data
        </button>
        <button onClick={() => {
            deleteCurrentUser();
        }} className={styles.signin_button}>Delete Current User
        </button>
        <button onClick={() => {
            displayAll();
        }} className={styles.signin_button}>Display All Users
        </button>
        <div>
            {
                userArray.map((item, index) => {
                    return <div key={index}>{item}</div>
                })
            }
        </div>
        <ToastContainer/>
    </div>);
}

export default Profile;