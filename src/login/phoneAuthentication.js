import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import axios from "axios";

function PhoneAuthentication () {
    const navigate = useNavigate();
    const pageMove = () =>{ navigate("/codeSend") }

    const url = "/api/sms/authentication"
    const config = {"Content-Type": 'application/json'}

    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const handName = (e) => {setName(e.target.value)};
    const handPhone = (e) => {
        let value = e.target.value
        value = value.replace(/[^0-9]/g, '');
        if (value.length === 11) {
            value = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
        setPhone(value);
    };

    /**
     * Axios 휴대폰 인증
     */
    const authentication = () => {
        if (name.trim() === '') {
            alert('이름을 입력해주세요');
            return;
        }
        if (phone.trim() === '') {
            alert('휴대폰번호를 입력해 주세요')
            return;
        }
        axios.post(url, {name: name, phone: phone}, config)
            .then(function (response) {
                if (response.data.code === 200) {
                    alert(response.data.message);
                    pageMove();
                } else {
                    return;
                }
            })
            .catch(error => console.log(error));
    }

    return (
        <div>
        <Form className="formText">
            <h2>휴대폰 본인인증</h2>
            <br/>
            <Form.Group className="mb-3" id="test">
            <Col sm="2" className="inputBox">
                이름
                <Form.Control type="text" name="name" value={name} onChange={handName}/>
            </Col>
            </Form.Group>
            <br/>
            <Form.Group className="mb-3">
                <Col sm="2" className="inputBox">
                    휴대폰번호
                    <Form.Control type="text"  name="phone" value={phone} onChange={handPhone} placeholder="000-0000-0000"/>
                </Col>
            </Form.Group>
            <Button veriant="primary" type="button" onClick={authentication}>본인인증</Button>
        </Form>
        </div>
    )
}
export default PhoneAuthentication;