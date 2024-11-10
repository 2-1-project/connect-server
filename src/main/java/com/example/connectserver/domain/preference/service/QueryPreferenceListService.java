package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.QueryPreferenceListResponse;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QueryPreferenceListService {

    private final UserRepository userRepository;

    public List<QueryPreferenceListResponse> getBusinessCards() {
        return userRepository.findAll().stream()
            .map(user -> new QueryPreferenceListResponse(user.getUsername(), user.getIntro()))
            .collect(Collectors.toList());
    }
}
