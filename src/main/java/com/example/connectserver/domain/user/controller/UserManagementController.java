package com.example.connectserver.domain.user.controller;

import com.example.connectserver.domain.user.dto.InstagramUserUpdate;
import com.example.connectserver.domain.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PutMapping("/user/instagram_update")
    public String updateInstaUser(InstagramUserUpdate update) {
        userManagementService.OAuthUserUpdate(update);
        return "redirect:/";
    }

    @GetMapping("/test_null")
    public String testNull() {
        return "null";
    }
}
