package com.bungeobbang.app.view.memberController;

import com.bungeobbang.app.biz.member.MemberDTO;
import com.bungeobbang.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UpdatePwController { // 비빌번호 변경 로직
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping(value="/setPw.do") // 비빌번호 수정 controller
	public String updatePW(MemberDTO memberDTO) {
		
		// memberDTO.setCondition : PASSWORD_UPDATE 값 넣어주기
		// memberDTO.set으로 memberNum, password 값 넣기
		memberDTO.setCondition("UPDATE_PASSWORD_CONDTION");
		//FIXME 선언한 적 없는 변수 호출 확인 바람 (주석처리)
//		memberDTO.setMemberNum(memberNum);
//		memberDTO.setMemberPassword(memberPassword);

		// memberDAO.update을 사용하여 memberDTO 업데이트
		// boolean flag에 반환값 저장
		boolean flag = memberService.update(memberDTO);

		// 업데이트에 성공했다면
		// flag가 false이면
		if(!flag) {
			return "redirect:error.do";
		}

		// 이동할 페이지 : loginPage.do
		return "redirect:login.do";

	}
}