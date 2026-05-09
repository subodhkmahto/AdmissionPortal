package com.portal.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long candidateUserId;

    @Column(nullable = false, length = 10)
    private String type; // EMAIL, SMS

    @Column(nullable = false, length = 150)
    private String recipient; // The email address or mobile number

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String messageBody;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "SENT"; // SENT, FAILED, DELIVERED

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sentAt;
}
