package com.example.connectserver.domain.user.service;

import com.example.connectserver.domain.user.dto.InstagramUserUpdate;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;

    public void OAuthUserUpdate(InstagramUserUpdate userUpdate) {
        UserEntity user = userRepository.findByInstagramId(userUpdate.getInstaId());
        user.setUsername(userUpdate.getUsername());
        user.setPassword(userUpdate.getPassword());
        userRepository.save(user);
    }

    public UserEntity getUserByInstaId(String InstaId) {
        UserEntity user = userRepository.findByInstagramId(InstaId);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }
}
