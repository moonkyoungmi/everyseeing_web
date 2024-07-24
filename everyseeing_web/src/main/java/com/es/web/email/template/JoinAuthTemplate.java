package com.es.web.email.template;

public class JoinAuthTemplate extends BasicTemplate {

	private String authNum;
	
	public JoinAuthTemplate() {
		setTitle("[Every Seeing] 이메일 인증번호 안내");
	}

	public String getAuthNum() {
		return authNum;
	}

	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}
	
	@Override
	public String getContent() {
		StringBuilder content = new StringBuilder();
		
		content.append("<h1> 인증번호 : ");
			content.append(authNum);
		content.append("</h1>");
		
		return content.toString();
	}
}
