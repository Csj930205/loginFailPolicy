import React, {} from "react";
import { useNavigate , useLocation } from "react-router-dom";
import Button from "react-bootstrap/Button";


function LoginFail() {
    const location = useLocation();
    const navigate = useNavigate();
    const loginPage = () => {
        navigate("/login")
    }

    const failCount = location.state.value;
    return (
        <div className="loginFailText">
            <h2>로그인 실패</h2>
            <br/>
            <b>남은 횟수 : {5 - failCount}</b>
            <br/><br/>
            <Button onClick={loginPage}>로그인 페이지 이동</Button>
        </div>
    )
}
export default LoginFail