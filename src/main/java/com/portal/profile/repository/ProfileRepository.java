package com.portal.profile.repository;

import com.portal.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    // Allows searching for a candidate profile by their IAM user ID
    Optional<Profile> findByUserId(Long userId);
    
    boolean existsByAadharNumber(String aadharNumber);
}
