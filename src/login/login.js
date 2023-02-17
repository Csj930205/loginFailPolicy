import './css/login.css';
import React, { useState } from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Button  from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Test from "./loginFail";

function Login() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const url = '/api/auth/login'
    const config = {"Content-Type" : 'application/json'}
    const navigate = useNavigate();

    const handleId = (e) => {setId(e.target.value)};
    const handlePw = (e) => {setPw(e.target.value)};

    /**
     * Axios Login
     */
    const onClickLogin = () => {
        if (id.trim() === '') {
            alert('아이디를 입력해주세요')
            return;
        }
        if (pw.trim() === '') {
            alert('패스워드를 입력해 주세요.')
            return;
        }
        axios.post(url, { id: id, pw: pw }, config)
            .then(function (response) {
                const fail = () => {
                    navigate("/loginFail", {state: {value: response.data.failCount}})
                }

                if (response.data.code === 400 || response.data.code === 404 || response.data.code === 403) {
                    if (response.data.enabled === true) {
                        alert("아이디 또는 패스워드가 일치하지 않습니다.");
                        fail();
                    } else {
                        alert(response.data.message);
                        window.location.href = "/loginLocked"
                    }
                } else {
                    alert('로그인 성공');
                    window.location.href = "/"
                }
            })
            .catch(error => console.log(error.message))
    }
    return (
        <Form className="formText">
            <h2>로그인</h2>
            <br/>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                아이디
                    <Form.Control type='text' name='id' value={id} onChange={handleId} />
                </Col>
            </Form.Group>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    패스워드
                    <Form.Control type="password" name="pw" value={pw} onChange={handlePw}></Form.Control>
                </Col>
            </Form.Group>
            <Button variant='primary' type='button' onClick={onClickLogin}>로그인</Button>
        </Form>

    )
}
export default Login;