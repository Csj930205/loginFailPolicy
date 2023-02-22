import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";

function CodeSend() {
    const navigate = useNavigate();
    const url = "/api/sms/codeCompare";
    const config = {"Content-Type" : 'application/json'};

    const [randomCode, setCode] = useState('');
    const handleCode = (e) => { setCode(e.target.value) };

    /**
     * Axios 인증 코드 비교 요청
     */
    const codeSend = () =>{
        if (randomCode.trim() === '') {
            alert('인증번호를 입력해주세요.');
            return;
        }
        axios.post(url, {randomCode : randomCode}, config)
            .then(function (response) {
                const pageMove = () => {navigate('/passwordChange', {state: {value: response.data.memberId}})}
                if (response.data.code === 200) {
                    alert('인증에 성공하였습니다.');
                    pageMove();
                } else {
                    alert('인증에 실패하였습니다.')
                    return;
                }
            })
            .catch(error => {console.log(error.message)})
    }

    return (
        <div>
            <Form className="formText">
                <h3>인증번호 입력</h3>
                <br/>
                <Form.Group>
                    <Col sm="2" className="inputBox">
                        <Form.Control type="text" name="code" value={randomCode} onChange={handleCode}/>
                    </Col>
                    <br/>
                    <Button veriant="primary" type="button" onClick={codeSend}>인증</Button>
                </Form.Group>
            </Form>
        </div>
    );
}

export default CodeSend;