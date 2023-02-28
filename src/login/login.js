import './css/login.css';
import React, { useState } from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Button  from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";

function Login({}) {
    const url = '/api/auth/login'
    const config = {"Content-Type" : 'application/json'}
    const navigate = useNavigate();
    const loginLocked = () => { navigate("/loginLocked") }

    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [phone, setPhone] = useState('')
    const [name, setName] = useState('')
    const [randomCode, setRandomCode] = useState('');

    const handleId = (e) => {setId(e.target.value)};
    const handlePw = (e) => {setPw(e.target.value)};
    const handlePhone = (e) => {
        let value = e.target.value
        value = value.replace(/[^0-9]/g, '');
        if (value.length === 11) {
            value = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
        setPhone(value);
    };
    const handleName = (e) => {setName(e.target.value)};
    const handleRandomCode = (e) => {setRandomCode(e.target.value)}


    const [phoneHidden, setPhoneHidden] = useState(true);
    const [phoneButton, setPhoneButton] = useState(true);
    const [authenticationButton, setAuthenticationButton] = useState(true);
    const [loginButton, setLoginButton] = useState(false);
    const [authenticationHidden, setAuthenticationHidden] = useState(true);
    const [codeDisabled, setCodeDisabled] = useState(true);

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
                        alert("아이디 또는 패스워드가 일치하지 않습니다. 실패횟수 : " + response.data.failCount);
                        if ( response.data.failCount > 2 && response.data.failCount < 5) {
                            setPhoneHidden(false);
                            setPhoneButton(false);
                            setLoginButton(true);
                            return;
                        } else {
                            fail();
                        }
                    } else {
                        alert(response.data.message);
                        loginLocked();
                    }
                } else {
                    alert('로그인 성공');
                    localStorage.setItem('isLogin', 'true');
                    window.location.href = "/"

                }
            })
            .catch(error => console.log(error))
    }

    /**
     * 3회 이상 오류 시 휴대폰 인증
     */
    const phoneAuthentication = () => {
        if (phone.trim() === '') {
            alert('휴대폰번호를 입력해주세요.');
            return
        }
        axios.post('/api/sms/authentication', {phone : phone, name : name}, config)
            .then(function (response) {
                if (response.data.code === 200) {
                    alert('인증번호가 발송되었습니다.')
                    setPhoneHidden(true);
                    setCodeDisabled(false);
                    setPhoneButton(true);
                    setAuthenticationHidden(false);
                    setAuthenticationButton(false);
                    return;
                } else {
                    alert(response.data.message)
                    return;
                }
            })
            .catch(error => console.log(error))
    }

    /**
     * 휴대폰 인증 코드 발송
     */
    const randomCodeSend = () => {
        if (randomCode.trim() === '' ) {
            alert('인증번호를 입력해주세요.')
            return;
        }
        axios.post('/api/sms/codeCompare', {randomCode : randomCode}, config)
            .then(function (response) {
                if (response.data.code === 200) {
                    alert('인증이 완료되었습니다. 로그인을 다시 진행해주세요.')
                    setAuthenticationHidden(true);
                    setAuthenticationButton(true);
                    setLoginButton(false);
                    setPhone('');
                    setName('');
                    setRandomCode('');
                    return;
                } else {
                    alert(response.data.message);
                }
            })
            .catch(error => console.log(error));
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
            <Form.Group className="mb-3" hidden={phoneHidden}>
                <Col sm="2" className="inputBox">
                    이름
                    <Form.Control type="text" name="name" value={name} onChange={handleName}></Form.Control>
                </Col>
            </Form.Group>
            <Form.Group className="mb-3" hidden={phoneHidden}>
                <Col sm="2" className="inputBox">
                    휴대폰인증
                    <Form.Control type="text" name="phone" value={phone} onChange={handlePhone}></Form.Control>
                </Col>
            </Form.Group>
            <Form.Group className="mb-3" hidden={authenticationHidden}>
                <Col sm="2" className="inputBox">
                    인증번호입력
                    <Form.Control disabled={codeDisabled} type="text" name="randomCode" value={randomCode} onChange={handleRandomCode}></Form.Control>
                </Col>
            </Form.Group>
            <Button variant='primary' type='button' onClick={onClickLogin} disabled={loginButton}>로그인</Button>
            <br/><br/>
            <Button variant="danger" type="button" onClick={phoneAuthentication} hidden={phoneButton}>인증요청</Button>
            <br/><br/>
            <Button variant="danger" type="button" onClick={randomCodeSend} hidden={authenticationButton}>인증</Button>
        </Form>
    )
}
export default Login;