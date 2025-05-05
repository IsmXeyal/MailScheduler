package com.scheduler.MailScheduler.service.impl;

import com.scheduler.MailScheduler.exception.EmailSendException;
import com.scheduler.MailScheduler.service.EmailSenderService;
import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ismxeyal2003@gmail.com", "MailScheduler");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException("Email sending failed", e);
        }
    }
}