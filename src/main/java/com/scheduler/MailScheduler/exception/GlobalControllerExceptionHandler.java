package com.scheduler.MailScheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    // Handle EmailSendException
    @ResponseBody
    @ExceptionHandler(EmailSendException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleEmailSendException(EmailSendException ex) {
        return ex.getMessage();
    }
}
