import './App.css';
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import Home from './login/home';
import Login from './login/login';
import LoginFail from "./login/loginFail";
import LoginLocked from "./login/loginLocked";
import PhoneAuthentication from "./login/phoneAuthentication";
import CodeSend from "./login/codeSend";
import PasswordChange from "./login/passwordChange";
import Signup from "./sign/signup";

function App() {
  return (
    <BrowserRouter>
        <Nav variant="tabs">
            <Nav.Item>
                <Nav.Link  as={Link} to='/'> Home </Nav.Link>
            </Nav.Item>
            <Nav.Item>
                <Nav.Link  as={Link} to='/login'> login </Nav.Link>
            </Nav.Item>
            <Nav.Item>
                <Nav.Link  as={Link} to='/signup'> signup </Nav.Link>
            </Nav.Item>
        </Nav>

      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login />} />
        <Route path='/loginFail' element={<LoginFail />} />
        <Route path='/loginLocked' element={<LoginLocked />} />
        <Route path='/phoneAuthentication' element={<PhoneAuthentication />} />
        <Route path='/codeSend' element={<CodeSend />} />
        <Route path='/passwordChange' element={<PasswordChange />} />
        <Route path='/signup' element={<Signup />} />
      </Routes>

    </BrowserRouter>
  );
}

export default App;
