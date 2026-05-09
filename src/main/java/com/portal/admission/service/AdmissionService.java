package com.portal.admission.service;

import com.portal.admission.dto.ApplicationRequest;
import com.portal.admission.entity.Application;
import com.portal.admission.entity.Course;
import com.portal.admission.repository.ApplicationRepository;
import com.portal.admission.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final ApplicationRepository applicationRepository;
    private final CourseRepository courseRepository;

    /**
     * Retrieve all active courses a Candidate can apply for.
     */
    public List<Course> getAvailableCourses() {
        return courseRepository.findByIsActiveTrue();
    }

    /**
     * Submits a fresh application for a student.
     * Prevents duplicate applications for the same course.
     */
    @Transactional
    public Application submitApplication(ApplicationRequest request) {
        
        // 1. Verify Course Exists & Active
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
                
        if (!course.isActive()) {
            throw new IllegalStateException("This course is no longer accepting applications.");
        }

        // 2. Prevent Double Applying
        if (applicationRepository.existsByCandidateUserIdAndCourseId(request.getCandidateUserId(), request.getCourseId())) {
            throw new IllegalArgumentException("Candidate has already applied for this course.");
        }

        // 3. Generate Unique Application ID (Example: APP-2026-XXXX)
        String appNumber = "APP-" + java.time.Year.now().getValue() + "-" 
                           + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Application newApplication = Application.builder()
                .applicationNumber(appNumber)
                .candidateUserId(request.getCandidateUserId())
                .courseId(request.getCourseId())
                .status("SUBMITTED") // Move straight to submitted for example purposes
                .build();

        return applicationRepository.save(newApplication);
    }

    /**
     * Allows a candidate to fetch all their applications.
     */
    public List<Application> getCandidateApplications(Long userId) {
        return applicationRepository.findByCandidateUserId(userId);
    }
}
