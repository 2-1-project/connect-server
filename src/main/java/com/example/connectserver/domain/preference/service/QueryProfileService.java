package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.PreferenceItem;
import com.example.connectserver.domain.preference.dto.ProfileResponse;
import com.example.connectserver.domain.preference.repository.PreferenceRepository;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryProfileService {

    private final UserRepository userRepository;
    private final PreferenceRepository preferenceRepository;

    public ProfileResponse getProfileByUsername(String instagramUsername) {
        UserEntity user = userRepository.findByInstagramUsername(instagramUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<PreferenceItem> preferences = preferenceRepository.findByUser(user)
            .stream()
            .map(preference -> new PreferenceItem(preference.getQuestion(), preference.getAnswer()))
            .collect(Collectors.toList());

        return new ProfileResponse(
            user.getInstagramUsername(),
            user.getIntro(),
            preferences
        );
    }
}
