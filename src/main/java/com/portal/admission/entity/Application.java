package com.portal.admission.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "adm_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String applicationNumber;

    /**
     * Loosely coupled to IAM Module User
     */
    @Column(nullable = false)
    private Long candidateUserId;

    /**
     * Loosely coupled to Course (no direct foreign key enforcement to allow future extraction)
     */
    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "DRAFT"; // DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED

    @Column(columnDefinition = "TEXT")
    private String reviewerNotes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime appliedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
