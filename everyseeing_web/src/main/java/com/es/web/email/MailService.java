package com.es.web.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(MailData mailData) throws Exception{
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	
		try {
			String email = mailData.getEmail();
			String title = mailData.getTitle();
			String content = mailData.getContent();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject(title); // 메일 제목
            mimeMessageHelper.setText(content, true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
            
		} catch (MessagingException e) {
			throw new RuntimeException (e);
		}
	}
}