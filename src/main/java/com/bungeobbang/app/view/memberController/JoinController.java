package com.bungeobbang.app.view.memberController;

import com.bungeobbang.app.biz.member.MemberDTO;
import com.bungeobbang.app.biz.member.MemberService;
import com.bungeobbang.app.view.util.FileUtil;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@Controller
public class JoinController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ServletContext servletContext;

    @PostMapping("/join.do") // 회원가입 controller
    public String join(MemberDTO memberDTO, MultipartFile file) {
        log.info("[Join] 시작");

        // 프로필 사진 업로드 처리
        String path = "uploads/"; // 업로드할 경로
        String fileName = FileUtil.createFileName(); // 파일 이름 생성
        String profilePicPath = FileUtil.insertFile(servletContext, path, file, fileName);
        log.info("[Join view에서 받은 프로필사진 경로] : {}", profilePicPath);

        memberDTO.setMemberProfileWay(profilePicPath);

        // 회원가입 요청
        boolean flag = memberService.insert(memberDTO);
        log.info("[Join insert 성공 실패 여부] : {}", flag);

        // 회원가입 실패 시 파일 삭제
        if (!flag) {
            return "errorPage"; // 에러 페이지로 이동
        }

        return "redirect:loginPage.do"; // 로그인 페이지로 리다이렉트
    }
}