package com.portal.upload.controller;

import com.portal.upload.entity.CandidateDocument;
import com.portal.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/document")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("userId") Long userId,
            @RequestParam("type") String documentType,
            @RequestParam("file") MultipartFile file) {
            
        CandidateDocument doc = uploadService.uploadS3Document(userId, documentType, file);
        return ResponseEntity.ok(Map.of(
                "message", "File processed securely to Cloud Bucket",
                "fileUrl", doc.getFileUrl()
        ));
    }
}
