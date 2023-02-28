import React from 'react';
import Nav from "react-bootstrap/Nav";
import { Link } from "react-router-dom";

function Navbar() {
    let isLogin = localStorage.getItem('isLogin');
    const logout = () => {
        alert('로그아웃 되었습니다.')
        isLogin = localStorage.setItem('isLogin', 'false');
        window.location.href = "/";
    }
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