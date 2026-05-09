package com.portal.profile.controller;

import com.portal.profile.dto.ProfileRequest;
import com.portal.profile.entity.Profile;
import com.portal.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProfile(@Valid @RequestBody ProfileRequest request) {
        Profile savedProfile = profileService.saveOrUpdateProfile(request);
        return ResponseEntity.ok(Map.of(
                "message", "Profile details saved successfully!",
                "profileId", savedProfile.getId()
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }
}
