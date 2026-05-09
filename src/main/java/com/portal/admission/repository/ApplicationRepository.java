package com.portal.admission.repository;

import com.portal.admission.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    List<Application> findByCandidateUserId(Long candidateUserId);
    
    Optional<Application> findByApplicationNumber(String applicationNumber);
    
    boolean existsByCandidateUserIdAndCourseId(Long candidateUserId, Long courseId);
}
