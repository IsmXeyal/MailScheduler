package com.scheduler.MailScheduler.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_outbox")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toEmail;
    private String subject;
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private int retryCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}