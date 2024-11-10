package com.example.connectserver.domain.preference.controller;

import com.example.connectserver.domain.preference.dto.BookmarkDeleteRequest;
import com.example.connectserver.domain.preference.dto.BookmarkRequest;
import com.example.connectserver.domain.preference.dto.CreatePreferenceRequest;
import com.example.connectserver.domain.preference.dto.PreferenceResponse;
import com.example.connectserver.domain.preference.service.*;
import com.example.connectserver.domain.user.dto.CustomUserDetails;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/preferences")
public class PreferenceController {

    private final CreatePreferenceService createPreferenceService;
    private final QueryPreferencesService queryPreferencesService;
    private final GenerateQRCodeService generateQRCodeService;
    private final BookmarkCreateService bookmarkCreateService;
    private final BookmarkDeleteService bookmarkDeleteService;
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

    @PostMapping("/bookmark")
    public ResponseEntity bookmarkCreate(@RequestBody BookmarkRequest request, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        bookmarkCreateService.excute(request,userDetails.id());
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/bookmark/delete")
    public ResponseEntity bookmarkDelete(@RequestBody BookmarkDeleteRequest request, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        bookmarkDeleteService.excute(request,userDetails.id());
        return ResponseEntity.ok(null);
    }

}
