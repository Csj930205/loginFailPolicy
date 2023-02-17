package com.example.loginfailpolicy.common;

import com.example.loginfailpolicy.domain.dto.MemberDto;
import com.example.loginfailpolicy.domain.entity.Member;
import com.example.loginfailpolicy.security.domain.CustomMemberDetail;
import com.example.loginfailpolicy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginCommon {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 검사(패스워드, 계정 잠금여부)
     * @param memberDto
     * @return
     */
    public Map<String, Object> LoginIdPwCompare(MemberDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        CustomMemberDetail memberId = memberService.loadUserByUsername(memberDto.getId());

        if (memberId.isAccountNonLocked() == true) {
            if ( passwordEncoder.matches(memberDto.getPw(), memberId.getPassword())) {
                memberService.failCountClear(memberId.getUsername());
                result.put("result", "success");
                result.put("code", HttpStatus.OK.value());
            } else {
                Member failCountUp = memberService.failCountUp(memberDto.getId());
                result.put("result", "fail");
                result.put("code", HttpStatus.NOT_FOUND.value());
                result.put("failCount", failCountUp.getFailCount());
                result.put("enabled", failCountUp.isEnabled());
                result.put("message", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.FORBIDDEN.value());
            result.put("failCount", memberId.getFailCount());
            result.put("enabled", memberId.isAccountNonLocked());
            result.put("message", "계정이 잠금되었습니다.");
        }
        return result;
    }
}
