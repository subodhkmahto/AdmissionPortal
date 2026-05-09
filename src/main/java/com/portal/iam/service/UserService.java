package com.portal.iam.service;

import com.portal.iam.dto.AuthRequest;
import com.portal.iam.dto.RegisterRequest;
import com.portal.iam.entity.User;
import com.portal.iam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new User into the platform.
     * Validates if the username or email already exists.
     */
    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered!");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CANDIDATE") // Default role for admission candidates
                .isActive(true)
                .build();

        return userRepository.save(newUser);
    }

    /**
     * Authenticates a user securely matching hashed passwords.
     */
    public User authenticateUser(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (!user.isActive()) {
            throw new IllegalStateException("User account is disabled!");
        }

        return user;
    }
}
