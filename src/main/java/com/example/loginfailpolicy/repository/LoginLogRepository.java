package com.example.loginfailpolicy.repository;

import com.example.loginfailpolicy.domain.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}
