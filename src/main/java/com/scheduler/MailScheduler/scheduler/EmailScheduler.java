package com.scheduler.MailScheduler.scheduler;

import com.scheduler.MailScheduler.service.EmailProcessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);
    private final EmailProcessorService emailService;

    //@Scheduled(fixedRate = 15 * 60 * 1000)  // 15 minutes
    @Scheduled(fixedRate = 60 * 1000) // 1 minutes
    public void checkAndSendEmails() {
        logger.info("Scheduler triggered to process pending emails.");
        emailService.processEmails();
    }
}
