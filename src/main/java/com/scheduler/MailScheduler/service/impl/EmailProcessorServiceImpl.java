package com.scheduler.MailScheduler.service.impl;

import com.scheduler.MailScheduler.dto.EmailRequestDTO;
import com.scheduler.MailScheduler.exception.EmailSendException;
import com.scheduler.MailScheduler.model.*;
import com.scheduler.MailScheduler.repository.EmailOutboxRepository;
import com.scheduler.MailScheduler.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailProcessorServiceImpl implements EmailProcessorService {
    private final EmailOutboxRepository repository;
    private final EmailSenderService senderService;

    @Override
    public void processEmails() {
        List<EmailOutbox> emails = repository.findByStatusIn(List.of(EmailStatus.PENDING, EmailStatus.RETRYING));
        for (EmailOutbox email : emails) {
            try {
                senderService.sendEmail(email.getToEmail(), email.getSubject(), email.getBody());
                email.setStatus(EmailStatus.SUCCESS);
            } catch (EmailSendException e) {
                int retries = email.getRetryCount() + 1;
                email.setRetryCount(retries);
                email.setStatus(retries >= 5 ? EmailStatus.FAILED : EmailStatus.RETRYING);
            }
            email.setUpdatedAt(LocalDateTime.now());
            repository.save(email);
        }
    }

    @Override
    public void saveEmail(EmailRequestDTO dto) {
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
    }
}
