package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.PreferenceResponse;
import com.example.connectserver.domain.preference.repository.PreferenceRepository;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryPreferencesService {

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public List<PreferenceResponse> execute(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return preferenceRepository.findByUser(user).stream()
            .map(pref -> {
                PreferenceResponse response = new PreferenceResponse();
                response.setId(pref.getId());
                response.setUserId(user.getInstagramId());
                response.setQuestion(pref.getQuestion());
                response.setAnswer(pref.getAnswer());
                return response;
            })
            .collect(Collectors.toList());
    }
}
