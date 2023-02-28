package com.example.loginfailpolicy.domain.dto;

import com.example.loginfailpolicy.domain.entity.LoginLog;
import com.example.loginfailpolicy.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginLogDto {
    private String access;

    private String ip;

    private Member member;

    @Builder
    public LoginLogDto(String access, String ip) {
        this.access = access;
        this.ip = ip;
    }

    public LoginLog toEntity() {
        return LoginLog.builder()
                .access(access)
                .ip(ip)
                .member(member)
                .build();
    }
}
