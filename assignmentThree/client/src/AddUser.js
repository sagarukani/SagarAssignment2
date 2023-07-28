import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import styles from "./login.module.css";

function AddUser() {
    const [email, setEmail] = useState("");
    const [pass, setPass] = useState("");
    const [name, setName] = useState("");

    let navigate = useNavigate();

    const addUser = () => {

        let item = {"email": email,"password":pass,"name":name}

        let result = fetch("http://localhost:3005/user", {
            method: "POST",
            headers: {
                "env": "test",
                "content-type": "application/json"
            },
            body: JSON.stringify(item)
        }).then(response => response.text())
            .then(result => {
                console.log(result)
                let path = "/login";
                navigate(path);
            })
            .catch(error => console.log('error', error));
    };

    return (
        <div className={styles.login}>
            <input className={styles.input} value={name} onInput={e => setName(e.target.value)} type="name" placeholder="Name"/>
            <input className={styles.input} value={email} onInput={e => setEmail(e.target.value)} type="email" placeholder="Email"/>
            <input className={styles.input} value={pass} onInput={e => setPass(e.target.value)} type="text" placeholder="Password"/>
            <button onClick={() => {
                addUser();
            }} className={styles.signin_button}>Add User</button>
        </div>
    );
}

export default AddUser;