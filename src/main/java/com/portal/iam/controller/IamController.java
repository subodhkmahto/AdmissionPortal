package com.portal.iam.controller;

import com.portal.iam.dto.AuthRequest;
import com.portal.iam.dto.RegisterRequest;
import com.portal.iam.entity.User;
import com.portal.iam.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/iam")
@RequiredArgsConstructor
public class IamController {

    private final UserService userService;

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        return ResponseEntity.ok(Map.of(
            "module", "Identity and Access Management (IAM)",
            "status", "Running"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        User registeredUser = userService.registerUser(request);
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "userId", registeredUser.getId()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest request) {
        User authenticatedUser = userService.authenticateUser(request);
        return ResponseEntity.ok(Map.of(
                "message", "Authentication successful",
                "username", authenticatedUser.getUsername(),
                "role", authenticatedUser.getRole()
        ));
    }
}
