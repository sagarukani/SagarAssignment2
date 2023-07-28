import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import styles from "./login.module.css";

function Login() {
    const [email, setEmail] = useState("");
    const [pass, setPass] = useState("");

    let navigate = useNavigate();

    const addUser = () =>{
        let path = "/add";
        navigate(path);
    }

    const handleSubmit = () => {

        let item = {"email": email,"password":pass}

        let result = fetch("http://localhost:3005/login", {
            method: "POST",
            headers: {
                "env": "test",
                "content-type": "application/json"
            },
            body: JSON.stringify(item)
        }).then(response => response.text())
            .then(result => {
                console.log(result)

                let path = "/Profile";
                navigate(path,{state:{uid:result}});
            })
            .catch(error => console.log('error', error));
    };

    return (
        <div className={styles.login}>
            <input className={styles.input} value={email} onInput={e => setEmail(e.target.value)} type="email" placeholder="Email"/>
            <input className={styles.input} value={pass} onInput={e => setPass(e.target.value)} type="password" placeholder="Password"/>
            <button onClick={() => {
                handleSubmit();
            }} className={styles.signin_button}>Sign In</button>
            <button onClick={() => {
                addUser();
            }} className={styles.signin_button}>Add User</button>
        </div>
    );
}

export default Login;