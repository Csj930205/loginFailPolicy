package com.example.loginfailpolicy.domain.dto;

import com.example.loginfailpolicy.domain.entity.Member;
import com.example.loginfailpolicy.domain.entity.SmsCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsCodeDto {
    private String phone;

    private String randomCode;

    private Member member;

    @Builder
    public SmsCodeDto(String phone, String randomCode) {
        this.phone = phone;
        this.randomCode = randomCode;
    }

    public SmsCode toEntity() {
        return SmsCode.builder()
                .phone(phone)
                .randomCode(randomCode)
                .member(member)
                .build();
    }
}
