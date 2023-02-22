package com.example.loginfailpolicy.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seq;

    @Column(name = "member_id")
    private String id;

    @Column(name = "member_pw")
    private String pw;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_phone")
    private String phone;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_role")
    private String role;

    @Column(name = "member_del_yn", columnDefinition = "varchar(1) default 'N' ", insertable = false)
    private String delYn;

    @Column(name = "member_signup_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime signupDate;

    @Column(name = "member_modified_date", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Column(name = "member_fail_count", columnDefinition = "Integer default '0' ", insertable = false)
    private int failCount;

    @Column(name = "member_enabled", columnDefinition = "TINYINT default '1' ", insertable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<SmsCode> smsCodes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<LoginLog> loginLogs;

    @Builder
    public Member (Long seq, String id, String pw, String name, String phone, String email, String role, String delYn,
                   LocalDateTime signupDate, LocalDateTime modifiedDate, int failCount, boolean enabled, List<SmsCode> smsCodes, List<LoginLog> loginLogs) {
        this.seq = seq;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.delYn = delYn;
        this.signupDate = signupDate;
        this.modifiedDate = modifiedDate;
        this.failCount = failCount;
        this.enabled = enabled;
        this.smsCodes = smsCodes;
        this.loginLogs = loginLogs;
    }
}
