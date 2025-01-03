package org.jsp.ekart.helper;

import org.jsp.ekart.dto.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender {

	@Autowired

	JavaMailSender javaMailSender;

	@Autowired
	TemplateEngine templateEngine;

	public void send(Vendor vendor) {
		String email = vendor.getEmail();
		int otp = vendor.getOtp();
		String name = vendor.getName();

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom("tankdixit41@gmail.com", "Ekart Site");
			helper.setTo(email);
			helper.setSubject("Otp for Email Verification");
			Context context = new Context();
			context.setVariable("name", name);
			context.setVariable("otp", otp);
			String text = templateEngine.process("otp-email.html", context);
			helper.setText(text, true);
			javaMailSender.send(message);

		} catch (Exception e) {
			System.err.println("There is Some Issue");
		}

	}
}
