package com.example.connectserver.domain.user.controller;

import com.example.connectserver.domain.user.dto.InstagramUser;
import com.example.connectserver.domain.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PutMapping("/instagram_update")
    public String updateInstaUser(InstagramUser update) {
        userManagementService.OAuthUserUpdate(update);
        return "redirect:/";
    }

    @GetMapping("/info")
    public RequestEntity<?> getUserInfo(@PathVariable Long instaId){
        userManagementService.getUserByInstaId(instaId);
    }
}
