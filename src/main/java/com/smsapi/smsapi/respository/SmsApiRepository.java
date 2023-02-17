package com.smsapi.smsapi.respository;

import com.smsapi.smsapi.domain.entity.SmsApi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsApiRepository extends JpaRepository<SmsApi, Long> {
}
