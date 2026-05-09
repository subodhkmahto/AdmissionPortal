package com.portal.upload.repository;

import com.portal.upload.entity.CandidateDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateDocumentRepository extends JpaRepository<CandidateDocument, Long> {
    List<CandidateDocument> findByCandidateUserId(Long candidateUserId);
    boolean existsByCandidateUserIdAndDocumentType(Long candidateUserId, String documentType);
}
