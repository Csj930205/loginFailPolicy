package com.example.loginfailpolicy.controller;

import com.example.loginfailpolicy.common.LoginCommon;
import com.example.loginfailpolicy.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginCommon loginCommon;

    /**
     * 로그인 요청
     * @param memberDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody MemberDto memberDto) {
        Map<String, Object> result = loginCommon.LoginIdPwCompare(memberDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
