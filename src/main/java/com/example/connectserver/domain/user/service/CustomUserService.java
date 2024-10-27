package com.example.connectserver.domain.user.service;

import com.example.connectserver.domain.user.dto.CustomUserDetails;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return CustomUserDetails.builder()
                .username(username)
                .password(user.getPassword())
                .build();
    }
}
