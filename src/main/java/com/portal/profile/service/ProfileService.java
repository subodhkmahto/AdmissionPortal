package com.portal.profile.service;

import com.portal.profile.dto.ProfileRequest;
import com.portal.profile.entity.Profile;
import com.portal.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    /**
     * Create or update a candidate's profile attached to their User ID.
     */
    public Profile saveOrUpdateProfile(ProfileRequest request) {
        
        // Ensure Aadhar is unique if entering a new one
        if (request.getAadharNumber() != null) {
            Optional<Profile> checkingAadhar = profileRepository.findByUserId(request.getUserId());
            if (checkingAadhar.isEmpty() && profileRepository.existsByAadharNumber(request.getAadharNumber())) {
                throw new IllegalArgumentException("This Aadhar Number is already registered by another candidate.");
            }
        }

        Profile profile = profileRepository.findByUserId(request.getUserId())
                .orElse(new Profile());

        profile.setUserId(request.getUserId());
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setMobile(request.getMobile());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setGender(request.getGender());
        profile.setCategory(request.getCategory());
        profile.setAadharNumber(request.getAadharNumber());

        return profileRepository.save(profile);
    }

    /**
     * Retrieve a Candidate's Profile by their underlying User ID.
     */
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for User ID: " + userId));
    }
}
