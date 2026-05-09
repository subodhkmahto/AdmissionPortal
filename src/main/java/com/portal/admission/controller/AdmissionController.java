package com.portal.admission.controller;

import com.portal.admission.dto.ApplicationRequest;
import com.portal.admission.entity.Application;
import com.portal.admission.entity.Course;
import com.portal.admission.service.AdmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admission")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getActiveCourses() {
        return ResponseEntity.ok(admissionService.getAvailableCourses());
    }

    @PostMapping("/apply")
    public ResponseEntity<?> submitApplication(@Valid @RequestBody ApplicationRequest request) {
        Application savedApplication = admissionService.submitApplication(request);
        return ResponseEntity.ok(Map.of(
                "message", "Application submitted successfully!",
                "applicationNumber", savedApplication.getApplicationNumber(),
                "status", savedApplication.getStatus()
        ));
    }

    @GetMapping("/my-applications/{userId}")
    public ResponseEntity<List<Application>> getMyApplications(@PathVariable Long userId) {
        return ResponseEntity.ok(admissionService.getCandidateApplications(userId));
    }
}
