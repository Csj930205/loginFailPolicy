import './App.css';
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import React from "react";
import Home from './login/home';
import Login from './login/login';
import LoginFail from "./login/loginFail";
import LoginLocked from "./login/loginLocked";
import PhoneAuthentication from "./login/phoneAuthentication";
import CodeSend from "./login/codeSend";
import PasswordChange from "./login/passwordChange";
import Signup from "./sign/signup";
import Navbar from "./navbar";


function App() {
  return (
    <BrowserRouter>
        <Navbar/>
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
