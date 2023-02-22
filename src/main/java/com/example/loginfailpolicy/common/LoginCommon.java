package com.example.loginfailpolicy.common;

import com.example.loginfailpolicy.domain.dto.LoginLogDto;
import com.example.loginfailpolicy.domain.dto.MemberDto;
import com.example.loginfailpolicy.domain.entity.Member;
import com.example.loginfailpolicy.repository.LoginLogRepository;
import com.example.loginfailpolicy.security.domain.CustomMemberDetail;
import com.example.loginfailpolicy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginCommon {
    private final MemberService memberService;

    private final LoginLogRepository loginLogRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 검사(패스워드, 계정 잠금여부)
     * @param memberDto
     * @return
     */
    public Map<String, Object> LoginIdPwCompare(MemberDto memberDto, HttpServletRequest request) {
        LoginLogDto loginLogDto = new LoginLogDto();
        Map<String, Object> result = new HashMap<>();
        String ip = getClientIp(request);
        CustomMemberDetail memberId = memberService.loadUserByUsername(memberDto.getId());

        if (memberId.isAccountNonLocked() == true) {
            if ( passwordEncoder.matches(memberDto.getPw(), memberId.getPassword())) {
                memberService.failCountClear(memberId.getUsername());
                loginLogDto.setAccess("성공");

                result.put("result", "success");
                result.put("code", HttpStatus.OK.value());
            } else {
                Member failCountUp = memberService.failCountUp(memberDto.getId());
                loginLogDto.setAccess("실패");

                result.put("result", "fail");
                result.put("code", HttpStatus.NOT_FOUND.value());
                result.put("failCount", failCountUp.getFailCount());
                result.put("enabled", failCountUp.isEnabled());
                result.put("message", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            loginLogDto.setAccess("실패");

            result.put("result", "fail");
            result.put("code", HttpStatus.FORBIDDEN.value());
            result.put("failCount", memberId.getFailCount());
            result.put("enabled", memberId.isAccountNonLocked());
            result.put("message", "계정이 잠금되었습니다.");
        }
        loginLogDto.setMember(memberId.getMember());
        loginLogDto.setIp(ip);
        loginLogRepository.save(loginLogDto.toEntity());

        return result;
    }

    /**
     * 사용자 IP 추출
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request) {
        String clientIp = null;
        boolean isIpInHeader = false;

        List<String> headerList = new ArrayList<>();
        headerList.add("X-Forwarded-For");
        headerList.add("HTTP_CLIENT_IP");
        headerList.add("HTTP_X_FORWARDED_FOR");
        headerList.add("HTTP_X_FORWARDED");
        headerList.add("HTTP_FORWARDED_FOR");
        headerList.add("HTTP_FORWARDED");
        headerList.add("WL-proxy-Client_IP");
        headerList.add("HTTP_VIA");
        headerList.add("IPV6_ADR");

        for (String header : headerList) {
            clientIp = request.getHeader(header);
            if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
                isIpInHeader = true;
                break;
            }
        }
        if (!isIpInHeader) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
