package com.personal.app.service;

import com.personal.app.dto.Transaction;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionMailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public TransactionMailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendFingerprintDebitEmail(String to, Transaction txn) throws MessagingException {
        Context ctx = new Context();
        ctx.setVariable("transactionId", txn.getId());
        ctx.setVariable("dateTime", getCurrentDateTime()); // format as string e.g. "2025-09-30 15:34"
        ctx.setVariable("amount", txn.getAmount());
        ctx.setVariable("maskedAccount", maskAccount(txn.getAccount()));
        ctx.setVariable("deviceInfo", txn.getDeviceId());
        ctx.setVariable("locationInfo", txn.getLocation());
        ctx.setVariable("status", txn.getStatus());
        ctx.setVariable("supportLink", "https://github.com/mithun-y");
        ctx.setVariable("supportPhone", "1800-XXX-XXX");
        ctx.setVariable("year", java.time.LocalDate.now().getYear());

        String html = templateEngine.process("transaction-email", ctx);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("Alert: Fingerprint Payment on your account");
        helper.setText(html, true);

        mailSender.send(message);
    }

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    private String maskAccount(String acct) {
        if (acct == null || acct.length() < 4) return "XXXX";
        String last4 = acct.substring(acct.length()-4);
        return "XXXX-XXXX-" + last4;
    }
}
