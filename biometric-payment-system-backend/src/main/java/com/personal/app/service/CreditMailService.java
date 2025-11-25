package com.personal.app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class CreditMailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBalanceUpdateMail(String to, double amount, double balance)
            throws MessagingException, IOException {

        // Load HTML file from resources/templates
        ClassPathResource resource = new ClassPathResource("templates/balance_update.html");
        String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Replace dynamic values
        html = html.replace("₹100", "₹" + amount)
                .replace("₹4500", "₹" + balance);

        // Create email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Balance Update");
        helper.setText(html, true);

        // Send
        mailSender.send(message);
    }
}
