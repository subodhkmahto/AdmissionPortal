package com.portal.payment.service;

import com.portal.payment.dto.PaymentInitiateRequest;
import com.portal.payment.dto.PaymentWebhookRequest;
import com.portal.payment.entity.Transaction;
import com.portal.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository transactionRepository;

    /**
     * Called when the user clicks 'Pay Now'.
     * Records intent and returns a unique Order ID which is passed to the Gateway.
     */
    @Transactional
    public Transaction initiatePayment(PaymentInitiateRequest request) {

        // Check if an existing successful payment already exists for this application
        if (transactionRepository.existsByApplicationIdAndStatus(request.getApplicationId(), "SUCCESS")) {
            throw new IllegalStateException("Payment has already been completed for this application.");
        }

        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();

        Transaction transaction = Transaction.builder()
                .orderId(orderId)
                .candidateUserId(request.getCandidateUserId())
                .applicationId(request.getApplicationId())
                .amount(request.getExpectedAmount())
                .status("PENDING")
                .build();

        return transactionRepository.save(transaction);
    }

    /**
     * The callback method triggered async by the Payment Gateway (Webhook).
     * Validates and updates the database flag securely.
     */
    @Transactional
    public Transaction handleWebhookCallback(PaymentWebhookRequest request) {

        Transaction transaction = transactionRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Order ID tracked."));

        if ("SUCCESS".equals(transaction.getStatus())) {
            // Already handled, ignore duplicate webhooks safely.
            return transaction;
        }

        transaction.setStatus(request.getStatus().toUpperCase());
        transaction.setGatewayReferenceId(request.getGatewayReferenceId());
        transaction.setPaymentMethod(request.getPaymentMethod());

        // Note: In a Modular Monolith with Spring ApplicationEvents,
        // here is where we would fire a PaymentSuccessEvent.
        // The Admissions module listens to that event and automatically 
        // marks the Application as 'APPROVED' without having a hard-coded Dependency!

        return transactionRepository.save(transaction);
    }
}
