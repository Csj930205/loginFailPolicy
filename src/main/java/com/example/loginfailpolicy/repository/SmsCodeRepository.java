package com.example.loginfailpolicy.repository;

import com.example.loginfailpolicy.domain.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    SmsCode findByRandomCode(String code);
}
