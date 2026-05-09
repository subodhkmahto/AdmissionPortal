package com.portal.upload.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "upload_documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long candidateUserId;

    @Column(nullable = false, length = 50)
    private String documentType; // AADHAR, MARKSHEET_10TH, PHOTOGRAPH, SIGNATURE

    @Column(nullable = false, length = 500)
    private String fileUrl; // Stores the secure internal AWS S3 Link

    @Column(nullable = false, length = 50)
    private String fileExtension; // e.g. pdf, png, jpg

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt;
}
