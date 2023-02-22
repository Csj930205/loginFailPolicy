package com.example.loginfailpolicy.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_log")
@NoArgsConstructor
@DynamicInsert
@Getter
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_log_seq")
    private Long seq;

    @Column(name = "login_log_access")
    private String access;

    @Column(name = "login_log_ip")
    private String ip;

    @Column(name = "login_log_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @Builder
    public LoginLog(Long seq, String access, String ip, LocalDateTime date , Member member) {
        this.seq = seq;
        this.access = access;
        this.ip = ip;
        this.date = date;
        this.member = member;
    }
}
