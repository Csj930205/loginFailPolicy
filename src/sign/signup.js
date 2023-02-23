import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";

function Signup() {
    const navigate = useNavigate();
    const home = () => { navigate('/') }

    const url = "/api/member/signup";
    const config = {"Content-Type": "application/json"}

    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [pwd, setPwd] = useState('');
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');

    const handleId = (e) => {setId(e.target.value)}
    const handlePw = (e) => {setPw(e.target.value)}
    const handlePwd = (e) => {setPwd(e.target.value)}
    const handleName = (e) => {setName(e.target.value)}
    const handleEmail = (e) => {setEmail(e.target.value)}
    const handlePhone = (e) => {setPhone(e.target.value)}

    const data = {id : id, pw: pw, name: name, email: email, phone: phone, role: "ROLE_USER"}
    const pwRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/i;

    /**
     * Axios 회원가입 요청
     */
    const signupMember = () => {
        if (id.trim() === '') {
            alert('아이디를 입력해주세요.');
            return;
        }
        if (pw.trim() === '') {
            alert('패스워드를 입력 해주세요.');
            return;
        }
        if (pw.match(pwRegEx) === null) {
            alert('비밀번호 형식을 확인해주세요.');
            return;
        }
        if (pwd.trim() === '') {
            alert('비밀번호 재입력을 확인해주세요.');
            return;
        }
        if (pw !== pwd) {
            alert('비밀번호가 다릅니다.재확인 해주세요.');
            return;
        }
        if (email.trim() === '') {
            alert('이메일을 입력해주세요')
            return;
        }
        if (email.match(emailRegEx) === null) {
            alert('이메일 형식이 잘못되었습니다.')
            return;
        }
        if (phone.trim() === '') {
            alert('휴대폰번호를 입력해주세요.')
        }
        axios.post(url, data, config)
            .then(function (response) {
                if (response.data.code === 200) {
                    alert(response.data.message)
                    home();
                } else {
                    alert(response.data.message)
                }
            })
            .catch(error => {console.log(error.message)})
    }

    return (
        <Form className="formText">
            <h3>회원가입</h3>
            <br/>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    아이디<Form.Control type="text" name="id" value={id} onChange={handleId}/>
                </Col>
            </Form.Group>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    패스워드<Form.Control type="password" name="pw" value={pw} onChange={handlePw}/>
                    최소 8자, 하나 이상의 대문자, 소문자, 숫자를 포함하여 입력해주세요.
                </Col>
            </Form.Group>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    패스워드 재입력<Form.Control type="password" name={pwd} value={pwd} onChange={handlePwd}/>
                </Col>
            </Form.Group>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    이름<Form.Control type="text" name="name" value={name} onChange={handleName}/>
                </Col>
            </Form.Group>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    이메일<Form.Control type="text" name="email" value={email} onChange={handleEmail}/>
                </Col>
            </Form.Group>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    휴대폰번호<Form.Control type="text" name="phone" value={phone} onChange={handlePhone}/>
                </Col>
            </Form.Group>
            <Button verient="primary" type="button" onClick={signupMember}>가입</Button>
        </Form>
    );
}

export default Signup;