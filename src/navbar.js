import React from 'react';
import Nav from "react-bootstrap/Nav";
import { Link } from "react-router-dom";
import axios from "axios";

function Navbar() {
    const isLogin = localStorage.getItem('isLogin');
    const url = '/api/auth/logout';
    const logout = () => {
        axios.post(url)
            .then(function (response) {
                alert(response.data.message);
                localStorage.clear();
                window.location.href = "/";
            })
            .catch(error => console.log(error))
    };
    return (
        <div>
            <Nav variant="tabs">
                <Nav.Item>
                    <Nav.Link  as={Link} to='/'> Home </Nav.Link>
                </Nav.Item>
            { isLogin  === 'true' ?  (
                <>
                    <Nav.Item>
                        <Nav.Link  as={Link} to='/'> 게시판 </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link  onClick={logout} as={Link} to='/'> 로그아웃 </Nav.Link>
                    </Nav.Item>
                </>
                ) : (
                <>
                    <Nav.Item>
                        <Nav.Link  as={Link} to='/login'> 로그인 </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link  as={Link} to='/signup'> 회원가입 </Nav.Link>
                    </Nav.Item>
                </>
                )
            }
            </Nav>
        </div>
    );
}

export default Navbar;