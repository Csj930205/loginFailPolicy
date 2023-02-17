package com.smsapi.smsapi.controller;

import com.smsapi.smsapi.service.SmsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SmsApiController {
    private final SmsApiService smsApiService;

    @PostMapping("smsCode")
    public ResponseEntity<Map<String, Object>> smsCodeSend(@RequestBody String phone) {

        Map<String, Object> result = smsApiService.smsApi(phone);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
