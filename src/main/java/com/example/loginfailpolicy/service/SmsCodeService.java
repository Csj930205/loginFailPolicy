package com.example.loginfailpolicy.service;

import com.example.loginfailpolicy.domain.dto.SmsCodeDto;
import com.example.loginfailpolicy.domain.entity.Member;
import com.example.loginfailpolicy.domain.entity.SmsCode;
import com.example.loginfailpolicy.repository.MemberRepository;
import com.example.loginfailpolicy.repository.SmsCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmsCodeService {
    private final SmsCodeRepository smsCodeRepository;
    private final MemberRepository memberRepository;

    /**
     * sms code 발송(외부 API 호출)
     * @param name
     * @param phone
     * @return
     */
    @Transactional
    public Map<String, Object> smsCodeSend(String name, String phone) {
        Member detailMember = memberRepository.findByNameAndPhone(name, phone);
        Map<String, Object> result = new HashMap<>();

        if (detailMember != null) {
            URI uri = UriComponentsBuilder
                    .fromUriString("http://192.168.4.8:8080")
                    .path("/api/smsCode")
                    .encode()
                    .build()
                    .toUri();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> success = restTemplate.postForEntity(uri, phone, Map.class);

            if (success != null) {
                SmsCodeDto smsCode = new SmsCodeDto();
                smsCode.setPhone((String) success.getBody().get("phone"));
                smsCode.setRandomCode((String) success.getBody().get("randomNumber"));
                smsCode.setMember(detailMember);
                smsCodeRepository.save(smsCode.toEntity());

                result.put("result", "success");
                result.put("code", HttpStatus.OK.value());
                result.put("message", "인증번호가 발송되었습니다.");
            } else {
                result.put("result", "fail");
                result.put("code", HttpStatus.BAD_REQUEST.value());
                result.put("message", "인증번호 발송에 실패하였습니다. 다시 시도해주세요.");
            }
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("message", "일치하는 정보가 없습니다.");
        }
        return result;
    }

    /**
     * 인증번호 비교.
     * @param smsCodeDto
     * @return
     */
    public Map<String, Object> codeCompare(SmsCodeDto smsCodeDto) {
        Map<String, Object> result = new HashMap<>();
        SmsCode smsCode = smsCodeRepository.findByRandomCode(smsCodeDto.getRandomCode());
        LocalDateTime now = LocalDateTime.now();
        long min = ChronoUnit.MINUTES.between(smsCode.getCheckDate(), now);

        if (smsCode != null && smsCode.getRandomCode().equals(smsCode.getRandomCode())) {
            if (min < 3) {
                result.put("result", "success");
                result.put("code", HttpStatus.OK.value());
                result.put("message", "인증에 성공하였습니다.");
                result.put("memberId", smsCode.getMember().getId());
            } else {
                result.put("result", "fail");
                result.put("code", HttpStatus.BAD_REQUEST.value());
                result.put("message", "인증번호 시간이 초과하였습니다.");
            }
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("message", "인증번호가 일치하지 않습니다");
        }
        return result;
    }
}
