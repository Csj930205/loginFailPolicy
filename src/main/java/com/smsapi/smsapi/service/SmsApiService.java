package com.smsapi.smsapi.service;

import com.smsapi.smsapi.domain.dto.SmsApiDto;
import com.smsapi.smsapi.respository.SmsApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsApiService {
    private final SmsApiRepository smsApiRepository;

    /**
     * 인증번호 발송 및 저장(예시)
     * @param phone
     * @return
     */
    @Transactional
    public Map<String, Object> smsApi(String phone) {
        Map<String, Object> result = new HashMap<>();
        StringBuffer randomNumber = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            randomNumber.append(random.nextInt(10));
        }
        String msg = "[본인확인] 인증번호 " + randomNumber + " 을 입력하세요.";

        SmsApiDto smsApi = new SmsApiDto();
        smsApi.setPhone(phone);
        smsApi.setMsg(msg);

        smsApiRepository.save(smsApi.toEntity());

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("phone", phone);
        result.put("randomNumber", randomNumber);

        return result;
    }
}
