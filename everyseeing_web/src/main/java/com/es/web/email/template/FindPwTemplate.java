package com.es.web.email.template;

public class FindPwTemplate extends BasicTemplate {

	private String tempPw;
	private String email;
	
	public FindPwTemplate() {
		setTitle("[Every Seeing] 비밀번호 찾기 안내");
	}

	public String getTempPw() {
		return tempPw;
	}

	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String getContent() {
		StringBuilder content = new StringBuilder();
		
		content.append("<h4> 임시 비밀번호: ");
			content.append(tempPw);
		content.append("</h4>");
		
		return content.toString();
	}
}
