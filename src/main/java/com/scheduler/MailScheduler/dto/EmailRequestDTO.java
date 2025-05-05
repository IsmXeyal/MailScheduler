package com.scheduler.MailScheduler.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class EmailRequestDTO {

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email address cannot be blank")
    private String toEmail;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    @NotBlank(message = "Body cannot be blank")
    private String body;
}
