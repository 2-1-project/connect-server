package com.example.connectserver.domain.user.service;

import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.global.auth.InstagramOAuthService;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest,OAuth2User> {

    private final InstagramOAuthService instagramOAuthService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = instagramOAuthService.loadUser(userRequest);
        Long id = Long.parseLong(Objects.requireNonNull(oAuth2User.getAttribute("id")));
        log.info("info: "+id);
        UserEntity user = userRepository.findByInstagramId(id);
        if (user == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setInstagramId(Long.valueOf(id));
            userEntity.setInstagramUsername(oAuth2User.getAttribute("username"));
            userEntity.setUsername(".");
            userEntity.setIntro(".");
            userRepository.save(userEntity);
            return oAuth2User;
        }

        userRequest.getClientRegistration().getRegistrationId();
        return oAuth2User;
    }
}
