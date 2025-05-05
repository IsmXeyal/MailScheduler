package com.scheduler.MailScheduler.service.impl;

import com.scheduler.MailScheduler.exception.EmailSendException;
import com.scheduler.MailScheduler.service.EmailSenderService;
import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            logger.info("Preparing to send email to {}", toEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ismxeyal2003@gmail.com", "MailScheduler");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", toEmail, e.getMessage());
            throw new EmailSendException("Email sending failed", e);
        }
    }
}