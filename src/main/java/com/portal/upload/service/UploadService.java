package com.portal.upload.service;

import com.portal.upload.entity.CandidateDocument;
import com.portal.upload.repository.CandidateDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final CandidateDocumentRepository documentRepository;

    /**
     * Secures a file upload stream directly to Cloud Storage (e.g., S3).
     */
    public CandidateDocument uploadS3Document(Long candidateUserId, String documentType, MultipartFile file) {

        // Validate Extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("Invalid file format.");
        }
        
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        // Security Validation
        if (!ext.equals("pdf") && !ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg")) {
            throw new IllegalArgumentException("For security reasons, only PDF and Images are allowed.");
        }

        // Prevent Duplicate Document Types (e.g. uploading two Signature files)
        if (documentRepository.existsByCandidateUserIdAndDocumentType(candidateUserId, documentType.toUpperCase())) {
            throw new IllegalStateException("You have already uploaded a " + documentType + ". Delete it first to re-upload.");
        }

        // Simulate S3 File Upload Path Generation
        String s3Url = "https://s3.amazonaws.com/admission-bucket/" + candidateUserId 
                     + "/" + documentType.toLowerCase() + "_" + UUID.randomUUID() + "." + ext;
                     
        CandidateDocument doc = CandidateDocument.builder()
                .candidateUserId(candidateUserId)
                .documentType(documentType.toUpperCase())
                .fileUrl(s3Url) // Abstract reference to S3
                .fileExtension(ext)
                .build();

        return documentRepository.save(doc);
    }
}
