package com.smsapi.smsapi.domain.dto;

import com.smsapi.smsapi.domain.entity.SmsApi;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsApiDto {

    private String phone;

    private String msg;

    @Builder
    public SmsApiDto(String phone, String msg) {
        this.phone = phone;
        this.msg = msg;
    }

    public SmsApi toEntity() {
        return SmsApi.builder()
                .phone(phone)
                .msg(msg)
                .build();
    }
}
