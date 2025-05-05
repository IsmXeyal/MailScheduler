package com.scheduler.MailScheduler.service.impl;

import com.scheduler.MailScheduler.dto.EmailRequestDTO;
import com.scheduler.MailScheduler.exception.EmailSendException;
import com.scheduler.MailScheduler.model.*;
import com.scheduler.MailScheduler.repository.EmailOutboxRepository;
import com.scheduler.MailScheduler.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailProcessorServiceImpl implements EmailProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(EmailProcessorServiceImpl.class);
    private final EmailOutboxRepository repository;
    private final EmailSenderService senderService;

    @Override
    public void processEmails() {
        List<EmailOutbox> emails = repository.findByStatusIn(List.of(EmailStatus.PENDING, EmailStatus.RETRYING));
        logger.info("Processing {} emails from email_outbox.", emails.size());

        for (EmailOutbox email : emails) {
            try {
                logger.info("Attempting to send email to: {}", email.getToEmail());
                senderService.sendEmail(email.getToEmail(), email.getSubject(), email.getBody());
                email.setStatus(EmailStatus.SUCCESS);
                logger.info("Email sent successfully to: {}", email.getToEmail());
            } catch (EmailSendException e) {
                int retries = email.getRetryCount() + 1;
                email.setRetryCount(retries);
                email.setStatus(retries >= 5 ? EmailStatus.FAILED : EmailStatus.RETRYING);

                if (retries >= 5) {
                    logger.error("Email to {} failed after {} retries. Marking as FAILED. Error: {}", email.getToEmail(), retries, e.getMessage());
                } else {
                    logger.warn("Email to {} failed. Retrying attempt {}. Error: {}", email.getToEmail(), retries, e.getMessage());
                }
            }
            email.setUpdatedAt(LocalDateTime.now());
            repository.save(email);
        }
    }

    @Override
    public void saveEmail(EmailRequestDTO dto) {
        logger.info("Saving new email request to: {}", dto.getToEmail());

        EmailOutbox email = EmailOutbox.builder()
                .toEmail(dto.getToEmail())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .status(EmailStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repository.save(email);
        logger.info("Email to {} has been saved to email_outbox with status PENDING.", dto.getToEmail());
    }
}
