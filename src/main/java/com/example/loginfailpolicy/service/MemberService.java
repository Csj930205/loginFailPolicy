package com.example.loginfailpolicy.service;

import com.example.loginfailpolicy.domain.dto.MemberDto;
import com.example.loginfailpolicy.domain.entity.Member;
import com.example.loginfailpolicy.repository.MemberRepository;
import com.example.loginfailpolicy.security.domain.CustomMemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * 회원 전체조회
     * @return
     */
    public List<Member> memberList() {
        List<Member> memberList = memberRepository.findAll();
        return memberList;
    }

    /**
     * 상세 회원조회
     * @param id
     * @return
     */
    public Member detailMember(String id) {
        Member detialMember = memberRepository.findById(id);
        return detialMember;
    }

    /**
     * 회원 가입
     * @param memberDto
     * @return
     */
    @Transactional
    public int insertMember(MemberDto memberDto) {
        Member findMember = memberRepository.findById(memberDto.getId());

        if (findMember == null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            memberDto.setPw(bCryptPasswordEncoder.encode(memberDto.getPw()));
            memberRepository.save(memberDto.toEntity());
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 회원 유무 검사
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public CustomMemberDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberId = memberRepository.findById(username);

        if (memberId != null) {
            return new CustomMemberDetail(memberId);
        } else {
            return null;
        }
    }

    /**
     * 로그인 실패 시 실패횟수 증가
     * @param id
     * @return
     */
    @Transactional
    public Member failCountUp(String id) {
        memberRepository.failCountUp(id);
        Member detailMember = memberRepository.findById(id);

        if (detailMember.getFailCount() == 5) {
            memberRepository.loginLocked(detailMember.getId());
        }
        return detailMember;
    }

    /**
     * 로그인 성공 시 실패횟수 초기화
     * @param id
     */
    @Transactional
    public void failCountClear(String id) {
        memberRepository.failCountClear(id);
    }

    /**
     * 비밀번호 재설정 및 계정 잠금 해제 및 카운트 초기화
     * @param memberDto
     * @return
     */
    @Transactional
    public Map<String, Object> passwordChange(MemberDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        Member detailMember = memberRepository.findById(memberDto.getId());

        if (detailMember != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            memberRepository.passwordChange(memberDto.getId(), bCryptPasswordEncoder.encode(memberDto.getPw()));
            result.put("result", "success");
            result.put("code", HttpStatus.OK.value());
            result.put("message", "비밀번호 변경이 완료되었습니다.");
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("message", "정보가 잘못되었습니다. 다시 시도해주세요.");
        }
        return result;

    }
}
