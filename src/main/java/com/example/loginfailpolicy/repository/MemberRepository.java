package com.example.loginfailpolicy.repository;

import com.example.loginfailpolicy.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 아이디를 이용한 회원 정보 조회
     * @param id
     * @return
     */
    Member findById(String id);

    Member findByNameAndPhone(String name, String phone);

    /**
     * 비밀번호 변경 및 카운트 초기화, 잠금 해제
     * @param id
     * @param pw
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set pw = :pw, failCount = 0, enabled = true where id = :id")
    void passwordChange(@Param("id") String id, @Param("pw") String pw);

    /**
     * 로그인 실패 시 실패 카운트 업
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set failCount = failCount + 1 where id = :id ")
    void failCountUp(@Param("id") String id);

    /**
     * 로그인 성공 시 실패 카운트 초기화
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set failCount = 0 where id = :id")
    void failCountClear(@Param("id") String id);

    /**
     * 로그인 5회 실패 시 계정 잠금.
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member set enabled = false where id = :id")
    void loginLocked(@Param("id") String id);


}
