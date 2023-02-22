package com.example.loginfailpolicy.domain.dto;

import com.example.loginfailpolicy.domain.entity.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private String id;

    private String pw;

    private String name;

    private String phone;

    @Pattern( regexp = "([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "이메일 형식이 잘못되었습니다.")
    @Email
    private String email;

    private String role;

    @Builder
    public MemberDto(String id, String pw, String name, String phone, String email, String role) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .pw(pw)
                .name(name)
                .phone(phone)
                .email(email)
                .role(role)
                .build();
    }
}
