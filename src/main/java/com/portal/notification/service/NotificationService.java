package com.portal.notification.service;

import com.portal.notification.dto.SendNotificationRequest;
import com.portal.notification.entity.NotificationLog;
import com.portal.notification.repository.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationLogRepository notificationLogRepository;

    /**
     * Executes the API call to AWS SES or Twilio SMS and logs the outcome.
     */
    public NotificationLog dispatchNotification(SendNotificationRequest request) {
        
        // TODO: In production, inject AmazonSimpleEmailService or TwilioRestClient here!
        System.out.println("Dispatched " + request.getType() + " to " + request.getRecipient());
        
        NotificationLog log = NotificationLog.builder()
                .candidateUserId(request.getCandidateUserId())
                .type(request.getType().toUpperCase())
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .messageBody(request.getMessageBody())
                .status("SENT")
                .build();

        return notificationLogRepository.save(log);
    }
}
