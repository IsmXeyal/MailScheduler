package com.scheduler.MailScheduler.controller;

import com.scheduler.MailScheduler.dto.EmailRequestDTO;
import com.scheduler.MailScheduler.service.EmailProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailProcessorService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
        emailService.saveEmail(emailRequestDTO);
        return ResponseEntity.ok("Email has been saved for processing.");
    }
}
