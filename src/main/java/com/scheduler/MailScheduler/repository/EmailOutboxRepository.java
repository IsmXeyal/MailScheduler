package com.scheduler.MailScheduler.repository;

import com.scheduler.MailScheduler.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, Long> {
    List<EmailOutbox> findByStatusIn(List<EmailStatus> statuses);
}
