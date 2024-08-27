package com.es.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.es.web.vo.RequestMap;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 회원가입
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/signUp")
	public Map<String, Object> signUp(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.signUp(param);
	}
	
	/**
	 * 인증번호 이메일 발송
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/send/mail")
	public Map<String, Object> sendMail(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.sendMail(param);
	}
	
	/**
	 * 이메일 인증번호 확인
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/check/auth")
	public Map<String, Object> checkAuthNum(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.checkAuthNum(param);
	}
	
	/**
	 * 계정에 따른 프로필 리스트
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/profile/list")
	public Map<String, Object> getProfileList(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.getProfileList(param);
	}
	
	/**
	 * 프로필 추가
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/profile/add")
	public Map<String, Object> addProfile(RequestMap reqMap, MultipartHttpServletRequest mRequest) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		MultipartFile mFile = mRequest.getFile("profile_file");
		
		return memberService.addProfile(param, mFile);
	}

	/**
	 * 프로필 수정
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/profile/modify")
	public Map<String, Object> modifyProfile(RequestMap reqMap, MultipartHttpServletRequest mRequest) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		MultipartFile mFile = mRequest.getFile("profile_file");
		
		return memberService.modifyProfile(param, mFile);
	}
	
	/**
	 * 프로필 선택
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/profile/select")
	public Map<String, Object> selectProfile(RequestMap reqMap, HttpServletRequest request) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.selectProfile(param, request);
 	}
	
	/**
	 * 프로필 정보
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/profile/info")
	public Map<String, Object> getProfileInfo(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.getProfileInfo(param);
 	}
	
	/**
	 * 비밀번호 찾기
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/find/pw")
	public Map<String, Object> findPw(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.findPw(param);
	}
	
	/**
	 * 비밀번호 변경
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/modify/pw")
	public Map<String, Object> modifyPw(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.modifyPw(param);
	}
}
