import React, {useState} from 'react';
import { useLocation, useNavigate} from "react-router-dom";
import axios from "axios";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";

function PasswordChange() {
    const location = useLocation();
    const navigate = useNavigate();
    const [pw, setPw] = useState('');
    const [pwd, setPwd] = useState('');
    const handlePw = (e) => {setPw(e.target.value)}
    const handlePwd = (e) => {setPwd(e.target.value)}
    const url = '/api/sms/passwordChange';
    const config = {"Content-Type": 'application/json'}
    const memberId = location.state.value;

    const passwordChange = () => {
        if (pw.trim() === '') {
            alert('비밀번호를 입력하세요.')
            return;
        }
        if (pwd.trim() === '') {
            alert('비밀번호 재입력을 입력해주세요.')
            return;
        }
        if ( !(pw === pwd) ) {
            alert('비밀번호와 비밀번호 재입력이 일치하지 않습니다.')
            return;
        }
        axios.patch(url, {id : memberId, pw : pw}, config)
            .then(function (response) {
                const home = () => {navigate('/')}
                if (response.data.code === 200) {
                    alert('비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요.')
                    home();
                } else {
                    alert('비밀번호 변경이 완료되지 않았습니다. 다시 시도해 주세요.')
                    return;
                }
            })
            .catch(error => {console.log(error.message)})
    }
    return (
        <div>
            <Form className="formText">
                <h2>비밀번호 재설정</h2>
                <br/>
                <Form.Group>
                    <Col sm="2" className="inputBox">
                        신규 비밀번호 입력: <Form.Control type="password" name="pw" value={pw} onChange={handlePw}/>
                    </Col>
                    <br/>
                    <Col sm="2" className="inputBox">
                        비밀번호 재입력: <Form.Control type="password" name="pwd" value={pwd} onChange={handlePwd}/>
                    </Col>
                    <br/>
                </Form.Group>
                <Button veriant="primary" type="button" onClick={passwordChange}>변경</Button>
            </Form>
        </div>
    );
}

export default PasswordChange;