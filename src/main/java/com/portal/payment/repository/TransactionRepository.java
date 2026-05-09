package com.portal.payment.repository;

import com.portal.payment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByOrderId(String orderId);

    List<Transaction> findByCandidateUserId(Long candidateUserId);
    
    List<Transaction> findByApplicationId(Long applicationId);
    
    // Helps check if a candidate already succeeded in passing payment for a specific app
    boolean existsByApplicationIdAndStatus(Long applicationId, String status);
}
