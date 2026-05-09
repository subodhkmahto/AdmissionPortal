package com.portal.payment.controller;

import com.portal.payment.dto.PaymentInitiateRequest;
import com.portal.payment.dto.PaymentWebhookRequest;
import com.portal.payment.entity.Transaction;
import com.portal.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@Valid @RequestBody PaymentInitiateRequest request) {
        Transaction transaction = paymentService.initiatePayment(request);
        return ResponseEntity.ok(Map.of(
                "message", "Payment Intent Created",
                "orderId", transaction.getOrderId(),
                "amount", transaction.getAmount()
        ));
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> gatewayWebhookCallback(@Valid @RequestBody PaymentWebhookRequest request) {
        paymentService.handleWebhookCallback(request);
        return ResponseEntity.ok(Map.of("status", "Reconciled safely"));
    }
}
