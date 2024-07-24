package com.es.web.email;

import com.es.web.email.template.BasicTemplate;

public class MailData {

	private String email;
	private String title;
	private String content;
	private BasicTemplate template;
	
	public MailData() {

	}
	
	public MailData(String email, BasicTemplate template) {
		this.email = email;
		this.template = template;
		this.title = template.getTitle();
		this.content = template.getContent();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}
	
	public BasicTemplate getTemplate() {
		return template;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setTemplate(BasicTemplate template) {
		this.template = template;
	}
	
}
