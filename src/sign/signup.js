import React, {useEffect, useRef, useState} from 'react';
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
    const [randomCode, setRandomCode] = useState('');
    const [signupButton, setSignupButton] = useState(true);
    const [randomCodeInput, setRandomCodeInput] = useState(true);
    const [authenticationButton, setAuthenticationButton] = useState(false);
    const [codeSendButton, setCodeSendButton] = useState(true)
    const [timerHidden, setTimerHidden] = useState(true);
    const [seconds, setSeconds] = useState(180);
    const intervalRef = useRef(null);

    const handleId = (e) => {setId(e.target.value)}
    const handlePw = (e) => {setPw(e.target.value)}
    const handlePwd = (e) => {setPwd(e.target.value)}
    const handleName = (e) => {setName(e.target.value)}
    const handleEmail = (e) => {setEmail(e.target.value)}
    const handlePhone = (e) => {
        let value = e.target.value
        value = value.replace(/[^0-9]/g, '');

        if (value.length === 11) {
            value = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
        setPhone(value);
    }
    const handleRandomCode = (e) => {setRandomCode(e.target.value)};

    const data = {id : id, pw: pw, name: name, email: email, phone: phone, role: "ROLE_USER"}
    const pwRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/i;

    /**
     * 타이머 텍스트
     * @param seconds
     * @returns {`${number} : ${string}${number}`}
     */
    const formatTimer = (seconds) => {
        const minutes = Math.floor(seconds / 60);
        const sec = seconds % 60;
        return `${minutes} : ${sec < 10 ? '0' : ''}${sec}`;
    }

    /**
     * 시작 타이머
     */
    const startTimer = () => {
        setSeconds(180);
        intervalRef.current = setInterval(() => {
            setSeconds(seconds => seconds - 1)
        }, 1000)
    }

    /**
     * 인증 성공 시 타이머 종료
     */
    const resetTimer = () => {
        // setSeconds(180);
        clearInterval(intervalRef.current);
    }

    /**
     * 인증시간 만료 시 종료
     */
    useEffect( () => {
        if (seconds === 0) {
            clearInterval(intervalRef.current);
            const timerText = document.getElementById('timerText');
            timerText.textContent = '인증시간이 초과하였습니다. 재시도 해주세요.';
            setCodeSendButton(true);
            setRandomCodeInput(true);
            setAuthenticationButton(false);
        }
    }, [seconds])

    /**
     * Axios 휴대폰 인증
     */
    const codeSend = () => {
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
        if (name.trim() === '') {
            alert('이름을 입력해주세요')
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
        const url = '/api/sms/authentication';
        const config = {"Content-Type" : 'application/json'}
        const data = {name : name, phone : phone};
        axios.post(url, data, config)
            .then(function (response) {
                if (response.data.code === 200) {
                    alert(response.data.message);
                    setRandomCodeInput(false);
                    setAuthenticationButton(true);
                    setCodeSendButton(false);
                    setTimerHidden(false);
                    startTimer();
                } else {
                    console.log(response.data.message);
                }
            }).catch(error => console.log(error))
    }

    /**
     * Axios 랜덤코드 전송
     */
    const codeSendAxios = () => {
        if (randomCode.trim() === '') {
            alert('인증번호를 입력해주세요.');
            return;
        }
        const url = '/api/sms/codeCompare';
        const data = {randomCode : randomCode}
        const config = {"Content-Type" : 'application/json'}
        axios.post(url, data, config)
            .then(function (response) {
                if (response.data.code === 200) {
                    alert(response.data.message);
                    setRandomCodeInput(true);
                    setCodeSendButton(true);
                    setSignupButton(false);
                    setTimerHidden(true)
                    resetTimer();
                } else {
                    alert(response.data.message);
                    setRandomCodeInput(true);
                    setCodeSendButton(true);
                    setAuthenticationButton(false);
                }
            }).catch(error => console.log(error));
    }

    /**
     * Axios 회원가입 요청
     */
    const signupMember = () => {
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
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox" hidden={randomCodeInput}>
                    인증번호 입력<Form.Control type="text" name="randomCode" value={randomCode} onChange={handleRandomCode}/>
                </Col>
                <span id="timerText" hidden={timerHidden}>{formatTimer(seconds)}</span>
            </Form.Group>
            <Button verient="primary" type="button" onClick={signupMember} disabled={signupButton}>가입</Button>
            <br/><br/>
            <Button verient="danger" type="button" onClick={codeSend} hidden={authenticationButton}>인증요청</Button>
            <Button verient="danger" type="button"  onClick={codeSendAxios} hidden={codeSendButton}>인증</Button>
        </Form>
    );
}

export default Signup;