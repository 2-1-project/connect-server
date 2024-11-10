package com.example.connectserver.domain.user.controller;

import com.example.connectserver.domain.user.dto.InstagramUserUpdate;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PutMapping("/user/instagram_update")
    public ResponseEntity<?> updateInstaUser(InstagramUserUpdate update) {
        userManagementService.OAuthUserUpdate(update);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@PathVariable Long instaId){
        UserEntity user = userManagementService.getUserByInstaId(instaId);
        return ResponseEntity.ok(user);
    }
}
