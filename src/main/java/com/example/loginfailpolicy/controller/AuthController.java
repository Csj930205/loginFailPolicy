package com.example.loginfailpolicy.controller;

import com.example.loginfailpolicy.common.LoginCommon;
import com.example.loginfailpolicy.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody MemberDto memberDto, HttpServletRequest request) {
        Map<String, Object> result = loginCommon.LoginIdPwCompare(memberDto, request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 로그아웃
     * @return
     */
    @PostMapping("logout")
    public ResponseEntity<Map<String, Object>> logout () {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("message", "로그아웃 되었습니다.");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
