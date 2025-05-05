package com.scheduler.MailScheduler.service;

import com.scheduler.MailScheduler.dto.EmailRequestDTO;

public interface EmailProcessorService {
    void processEmails();
    void saveEmail(EmailRequestDTO dto);
}
