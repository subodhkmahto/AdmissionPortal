package com.portal.notification.controller;

import com.portal.notification.dto.SendNotificationRequest;
import com.portal.notification.entity.NotificationLog;
import com.portal.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        NotificationLog log = notificationService.dispatchNotification(request);
        return ResponseEntity.ok(Map.of(
            "message", "Notification logged and successfully queued",
            "logId", log.getId()
        ));
    }
}
