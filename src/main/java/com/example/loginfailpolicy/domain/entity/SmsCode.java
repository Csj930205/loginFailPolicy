package com.example.loginfailpolicy.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms_code")
@Getter
@NoArgsConstructor
@DynamicInsert
public class SmsCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sms_code_seq")
    private Long seq;

    @Column(name = "sms_code_phone")
    private String phone;

    @Column(name = "sms_code_random")
    private String randomCode;

    @Column(name = "sms_code_checkDate", updatable = false)
    @CreationTimestamp
    private LocalDateTime checkDate;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Builder
    public SmsCode(Long seq, String phone, String randomCode, LocalDateTime checkDate, Member member) {
        this.seq = seq;
        this.phone = phone;
        this.randomCode = randomCode;
        this.checkDate = checkDate;
        this.member = member;
    }
}
