import React from "react";
import { useNavigate } from "react-router-dom";

import Button from "react-bootstrap/Button";


function LoginLocked() {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    const navigate = useNavigate();
    const authenticationPage = () => {
        navigate("/phoneAuthentication")
    }
    const home = () => {
        navigate("/")
    }

    return (
        <div className="authentication">
            <h2>계정이 잠금되었습니다.</h2>
            <br/>
            <b>본인인증 후 비밀번호를 변경하세요</b>
            <br/><br/>
            <Button type="button" onClick={authenticationPage}>휴대폰인증</Button>
            <br/><br/>
            <Button type="button" onClick={home}>메인</Button>
        </div>

        )
}
export default LoginLocked;