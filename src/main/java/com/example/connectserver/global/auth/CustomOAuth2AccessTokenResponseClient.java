package com.example.connectserver.global.auth;

import com.fasterxml.jackson.databind.util.Converter;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    @Value("${spring.security.oauth2.client.provider.instagram.token-uri}")
    private String url;
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter requestEntityConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();


    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        Assert.notNull(authorizationGrantRequest, "authorizationGrantRequest must not be null");
        System.out.println(authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        RequestEntity<?> request = requestEntityConverter.convert(authorizationGrantRequest);
        assert request != null;
        ResponseEntity<InstagramAccessToken> response = getResponse(request);
        InstagramAccessToken body = response.getBody();

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("access_token", body.getAccessToken());
        additionalParameters.put("user_id", body.getUserId());

        return OAuth2AccessTokenResponse
                .withToken(body.getAccessToken())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .additionalParameters(additionalParameters)
                .build();
    }

    public ResponseEntity<InstagramAccessToken> getResponse(RequestEntity<?> request) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(request.getUrl());
        try {
          log.info("in");
            return restTemplate.exchange(request, InstagramAccessToken.class);
        } catch (RestClientException e){
            System.out.println(e.getMessage());
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }





}
