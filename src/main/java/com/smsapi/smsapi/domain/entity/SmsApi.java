package com.smsapi.smsapi.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "smsApi")
@Getter
@NoArgsConstructor
@DynamicInsert
public class SmsApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smsAPi_seq")
    private Long seq;

    @Column(name = "smsApi_phone")
    private String phone;

    @Column(name = "smsAPi_call_back", columnDefinition = "varchar(15) default '010-9293-3284' ", updatable = false)
    private String callBack;

    @Column(name = "smsApi_msg")
    private String msg;

    @Column(name = "smsApi_send_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime sendDate;

    @Builder
    public SmsApi(Long seq, String phone, String callBack, String msg, LocalDateTime sendDate) {
        this.seq = seq;
        this.phone = phone;
        this.callBack = callBack;
        this.msg = msg;
        this.sendDate = sendDate;
    }
}
