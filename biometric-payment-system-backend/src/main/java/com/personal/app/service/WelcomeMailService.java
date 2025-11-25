package com.personal.app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class WelcomeMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendWelcomeEmail(String to, String name, String accountNumber,double amount) throws MessagingException {
        // Prepare Thymeleaf context
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("accountNumber", accountNumber);
        context.setVariable("amount",amount);

        // Process template into HTML
        String htmlContent = templateEngine.process("welcome-email", context);

        // Create email message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject("Welcome to Bio-Pay Bank 🎉");
        helper.setText(htmlContent, true); // true => HTML

        mailSender.send(mimeMessage);
    }
}

