package com.example.connectserver.domain.preference.controller;

import com.example.connectserver.domain.preference.dto.BookmarkDeleteRequest;
import com.example.connectserver.domain.preference.dto.BookmarkRequest;
import com.example.connectserver.domain.preference.dto.CreatePreferenceRequest;
import com.example.connectserver.domain.preference.dto.ProfileResponse;
import com.example.connectserver.domain.preference.dto.QRCodeResponse;
import com.example.connectserver.domain.preference.dto.QueryPreferenceListResponse;
import com.example.connectserver.domain.preference.dto.UpdatePreferenceRequest;
import com.example.connectserver.domain.preference.service.BookmarkCreateService;
import com.example.connectserver.domain.preference.service.BookmarkDeleteService;
import com.example.connectserver.domain.preference.service.CreatePreferenceService;
import com.example.connectserver.domain.preference.service.QueryPreferenceListService;
import com.example.connectserver.domain.preference.service.QueryProfileService;
import com.example.connectserver.domain.preference.service.UpdatePreferenceService;
import com.example.connectserver.domain.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/preferences")
public class PreferenceController {

    private final BookmarkCreateService bookmarkCreateService;
    private final BookmarkDeleteService bookmarkDeleteService;
    private final UpdatePreferenceService updatePreferenceService;
    private final QueryPreferenceListService queryPreferenceListService;
    private final QueryProfileService queryProfileService;
    private final CreatePreferenceService createPreferenceService;

    @PostMapping("/bookmark")
    public ResponseEntity<Object> bookmarkCreate(@RequestBody BookmarkRequest request, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        bookmarkCreateService.excute(request,userDetails.id());
        try {
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/bookmark")
    public ResponseEntity<Object> deleteBookmark(@RequestBody BookmarkDeleteRequest request, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        try {
            bookmarkDeleteService.excute(request, userDetails.id());
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<QRCodeResponse> createPreference(@RequestBody CreatePreferenceRequest request) {
        try {
            QRCodeResponse qrCodeResponse = createPreferenceService.execute(request);
            return ResponseEntity.ok(qrCodeResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<QRCodeResponse> updatePreference(@RequestBody UpdatePreferenceRequest request) {
        try {
            QRCodeResponse qrCodeResponse = updatePreferenceService.execute(request);
            return ResponseEntity.ok(qrCodeResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllBusinessCards() {
        List<QueryPreferenceListResponse> data = queryPreferenceListService.getBusinessCards();
        return ResponseEntity.ok().body(Map.of("data", data));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username) {
        ProfileResponse profileResponse = queryProfileService.getProfileByUsername(username);
        return ResponseEntity.ok(profileResponse);
    }
}
