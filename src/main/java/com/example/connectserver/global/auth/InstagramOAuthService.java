package com.example.connectserver.global.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class InstagramOAuthService extends DefaultOAuth2UserService {
    @Value("${spring.security.oauth2.client.provider.instagram.user-info-uri}")
    String userInfoUri;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String token = userRequest.getAccessToken().getTokenValue();
        if(token == null) {
            throw new OAuth2AuthenticationException("can use after login");
        }


        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> userInfo = restTemplate.getForObject(userInfoUri+token, Map.class);
        Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(collection, userInfo, token);



    }
}
