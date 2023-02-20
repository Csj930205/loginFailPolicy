package com.example.loginfailpolicy.controller;

import com.example.loginfailpolicy.domain.dto.MemberDto;
import com.example.loginfailpolicy.domain.dto.SmsCodeDto;
import com.example.loginfailpolicy.service.MemberService;
import com.example.loginfailpolicy.service.SmsCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class PhoneAuthenticationController {
    private final SmsCodeService smsCodeService;
    private final MemberService memberService;

    /**
     * Sms API 호출
     * @param memberDto
     * @return
     */
    @PostMapping("authentication")
    public ResponseEntity<Map<String, Object>> phoneAuthentication(@RequestBody MemberDto memberDto) {
        Map<String, Object> result = smsCodeService.smsCodeSend(memberDto.getName(), memberDto.getPhone());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * code 비교
     * @param smsCodeDto
     * @return
     */
    @PostMapping("codeCompare")
    public ResponseEntity<Map<String, Object>> codeCompare(@RequestBody SmsCodeDto smsCodeDto) {
        Map<String, Object> result = smsCodeService.codeCompare(smsCodeDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 비밀번호 변경
     * @param memberDto
     * @return
     */
    @PatchMapping("passwordChange")
    public ResponseEntity<Map<String, Object>> passwordChange(@RequestBody MemberDto memberDto) {
        Map<String, Object> result = memberService.passwordChange(memberDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
