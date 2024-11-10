package com.example.connectserver.global.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

@Slf4j
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

        log.info("token= ",token);
        log.info(userInfoUri+token);

        RestTemplate restTemplate = new RestTemplate();
        userInfoUri = userInfoUri+token;
        HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);

        Map<String,Object> userInfo = response.getBody();
        log.info("id",userInfo.get("id"));
        Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(collection, userInfo, "username");
    }

}
