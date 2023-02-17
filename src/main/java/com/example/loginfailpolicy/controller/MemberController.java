package com.example.loginfailpolicy.controller;

import com.example.loginfailpolicy.domain.dto.MemberDto;
import com.example.loginfailpolicy.domain.entity.Member;
import com.example.loginfailpolicy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입
     * @param memberDto
     * @return
     */
    @PostMapping("signup")
    public ResponseEntity<Map<String, Object>> signupMember(@RequestBody MemberDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        if (memberService.insertMember(memberDto) > 0) {
            result.put("result", "success");
            result.put("code", HttpStatus.OK.value());
        } else {
            result.put("result", "fail");
            result.put("code", HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 전체 리스트
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<Map<String, Object>> listMember() {
        List<Member> memberList = memberService.memberList();
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("memberList", memberList);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/detailList/{id}")
    public ResponseEntity<Map<String, Object>> detailList(@PathVariable("id") String id) {
        Member detailList = memberService.detailMember(id);
        Map<String, Object> result = new HashMap<>();

        result.put("result", "success");
        result.put("code", HttpStatus.OK.value());
        result.put("detailList", detailList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
