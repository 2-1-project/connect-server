package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.CreatePreferenceRequest;
import com.example.connectserver.domain.preference.dto.PreferenceResponse;
import com.example.connectserver.domain.preference.entity.PreferenceEntity;
import com.example.connectserver.domain.preference.repository.PreferenceRepository;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public PreferenceResponse execute(CreatePreferenceRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        PreferenceEntity preference = new PreferenceEntity();
        preference.setUser(user);
        preference.setQuestion(request.getQuestion());
        preference.setAnswer(request.getAnswer());

        PreferenceEntity savedPreference = preferenceRepository.save(preference);

        PreferenceResponse response = new PreferenceResponse();
        response.setId(savedPreference.getId());
        response.setUserId(user.getId());
        response.setQuestion(savedPreference.getQuestion());
        response.setAnswer(savedPreference.getAnswer());

        return response;
    }
}
