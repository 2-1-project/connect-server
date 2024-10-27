package com.example.connectserver.domain.preference.controller;

import com.example.connectserver.domain.preference.dto.CreatePreferenceRequest;
import com.example.connectserver.domain.preference.dto.PreferenceResponse;
import com.example.connectserver.domain.preference.service.CreatePreferenceService;
import com.example.connectserver.domain.preference.service.GenerateQRCodeService;
import com.example.connectserver.domain.preference.service.QueryPreferencesService;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/preferences")
public class PreferenceController {

    private final CreatePreferenceService createPreferenceService;
    private final QueryPreferencesService queryPreferencesService;
    private final GenerateQRCodeService generateQRCodeService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<PreferenceResponse> create(@RequestBody CreatePreferenceRequest request) {
        return ResponseEntity.ok(createPreferenceService.execute(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PreferenceResponse>> get(@PathVariable Long userId) {
        return ResponseEntity.ok(queryPreferencesService.execute(userId));
    }

    @GetMapping(value = "/qr/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getUserPreferencesQrCode(@PathVariable Long userId) throws Exception {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<PreferenceResponse> preferences = queryPreferencesService.execute(userId);
        byte[] qrCode = generateQRCodeService.execute(userId, user.getUsername(), preferences);

        return ResponseEntity.ok(qrCode);
    }
}
