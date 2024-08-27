package com.es.web.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.es.web.email.MailData;
import com.es.web.email.MailService;
import com.es.web.email.template.FindPwTemplate;
import com.es.web.email.template.JoinAuthTemplate;
import com.es.web.s3.S3Service;
import com.es.web.util.CommonUtil;
import com.es.web.util.SHAUtil;
import com.es.web.vo.Code;
import com.es.web.vo.ResponseMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private S3Service s3Service;
	
	@Value("${s3.member}")
	private String S3_MEMBER;

	@Value("${s3.profile}")
	private String S3_PROFILE;
	
	/**
	 * 회원가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> signUp(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		// 이메일 중복 확인
		if(memberMapper.duplicateEmailCheck(param) > 0) {
			return respMap.getResponseMap(Code.MEMBER_DUPLE_EMAIL);
		}
		
		// 이메일 인증 여부 확인
		Map<String, Object> data = memberMapper.emailAuthCheck(param);
		if(data != null) {
			String auth_yn = (String) data.get("check_yn");
			if(!auth_yn.equals("Y")) {
				return respMap.getResponseMap(Code.MEMBER_EMAIL_AUTH_FAIL);
			}
		} else {
			return respMap.getResponseMap(Code.MEMBER_EMAIL_AUTH_FAIL);
		}
		
		// 비밀번호 암호화
		String password = (String) param.get("password");
		String encPassword = SHAUtil.encrypt(password);
		param.put("enc_password", encPassword);
		
		// 회원가입
		if(memberMapper.signUp(param) <= 0) {
			return respMap.getErrorResponseMap();
		}
		param.put("idx_member", param.get("idx"));
		
		// 최초 프로필 1개 등록
		param.put("nickname", "기본");
		
		if(memberMapper.addProfile(param) <= 0) {
			return respMap.getResponseMap(Code.ERROR);
		}
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 인증번호 이메일 발송
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> sendMail(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		// 이메일 중복 확인
		if(memberMapper.duplicateEmailCheck(param) > 0) {
			return respMap.getResponseMap(Code.MEMBER_DUPLE_EMAIL);
		}
		
		String authNum = CommonUtil.makeRandStr(10);
		param.put("auth_num", authNum);
		
		// 인증번호 DB 저장
		memberMapper.saveAuthNum(param);
		
		// 메일 템플릿 설정
		String email = (String) param.get("email");
	 	JoinAuthTemplate template = new JoinAuthTemplate();
	 	template.setAuthNum(authNum);
 		
 		// 메일 발송
 		MailData mailData = new MailData(email, template);
 		mailService.sendMail(mailData);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 이메일 인증번호 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkAuthNum(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		Map<String, Object> data = memberMapper.getAuthNum(param);
		String savedAuthNum = (String) data.get("auth_number");
		String authNum = (String) param.get("auth_num");
		
		if(!savedAuthNum.equals(authNum)) {
			return respMap.getResponseMap(Code.MEMBER_EMAIL_AUTH_FAIL);
		}
		
		respMap.setBody("data", data);

		memberMapper.modifyAuthNumCheck(param);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 계정에 따른 프로필 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getProfileList(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		List<Map<String, Object>> list = memberMapper.getProfileList(param);

		respMap.setBody("list", list);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 프로필 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> addProfile(Map<String, Object> param, MultipartFile mFile) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		// 프로필 개수 확인
		int profileCnt = memberMapper.countProfile(param);
		if(profileCnt >= 4) {
			return respMap.getResponseMap(Code.MEMBER_PROFILE_IS_FULL);
		} else if(profileCnt <= 0) {
			return respMap.getResponseMap(Code.MEMBER_NOT_EXIST);
		}
		
		if(memberMapper.addProfile(param) <= 0) {
			return respMap.getResponseMap(Code.ERROR);
		}
		param.put("idx_profile", param.get("idx"));

		// 파일 처리
		if(mFile != null) {
			String fileName = mFile.getOriginalFilename();
			String uploadPath = S3_MEMBER + param.get("idx_member") + "/" + S3_PROFILE + param.get("idx_profile") + "/" + fileName;
			String s3Path = s3Service.uploadFile(mFile, uploadPath);
			param.put("profile_file", s3Path);
			param.put("file_name", fileName);

			if(memberMapper.modifyProfile(param) <= 0) {
				s3Service.deleteFile(s3Path);
				return respMap.getResponseMap(Code.ERROR);
			}
		}
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 프로필 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modifyProfile(Map<String, Object> param, MultipartFile mFile) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		Map<String, Object> data = memberMapper.getProfileInfo(param);
		if(data == null) {
			return respMap.getResponseMap(Code.ERROR);
		}
		
		// 파일 처리
		String s3Path = "";
		String basicPath = S3_MEMBER + param.get("idx_member") + "/" + S3_PROFILE + param.get("idx_profile") + "/";
		if(mFile != null) {
			// 기존 파일 삭제
			String prePath = basicPath + (String) data.get("file_name");
			s3Service.deleteFile(prePath );
			
			// 새 파일 저장
			String fileName = mFile.getOriginalFilename();
			String uploadPath = basicPath + fileName;
			s3Path = s3Service.uploadFile(mFile, uploadPath);
			param.put("profile_file", s3Path);
			param.put("file_name", fileName);
		}

		if(memberMapper.modifyProfile(param) <= 0) {
			if(mFile != null) {
				s3Service.deleteFile(s3Path);
			}
			return respMap.getResponseMap(Code.ERROR);
		}
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 프로필 선택
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectProfile(Map<String, Object> param, HttpServletRequest request) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		HttpSession session = request.getSession();
		session.setAttribute("login_profile", param.get("idx_profile"));
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 프로필 정보
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getProfileInfo(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		Map<String, Object> data = memberMapper.getProfileInfo(param);
		respMap.setBody("data", data);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 비밀번호 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findPw(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();

		// 회원 정보
		Map<String, Object> memberInfo = memberMapper.getMemberInfo(param);

		// 회원 유무 체크
		if(memberInfo == null) {
			return respMap.getResponseMap(Code.MEMBER_NOT_EXIST);
		}
		
		// 임시 비밀번호
		String tempPw = CommonUtil.makeRandStr(10);
		memberInfo.put("temp_pw", tempPw);
		memberInfo.put("temp_yn", "Y");
		
		// 메일 템플릿 설정
		String email = (String) memberInfo.get("email");
	 	FindPwTemplate template = new FindPwTemplate();
	 	template.setEmail(email);
	 	template.setTempPw(tempPw);
 		
 		// 메일 발송
 		MailData mailData = new MailData(email, template);
 		mailService.sendMail(mailData);
		
		// 임시 비밀번호 암호화 후 회원 정보 수정
		memberInfo.put("temp_pw", SHAUtil.encrypt(tempPw));
		memberMapper.modifyMember(memberInfo);
		
		return respMap.getResponseMap();
	}
}
