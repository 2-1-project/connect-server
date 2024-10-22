package com.example.connectserver.global.auth;

import com.fasterxml.jackson.databind.util.Converter;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    @Value("${spring.security.oauth2.client.provider.instagram.token-uri}")
    private String url;
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter requestEntityConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();


    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        Assert.notNull(authorizationGrantRequest, "authorizationGrantRequest must not be null");
        RequestEntity<?> request = requestEntityConverter.convert(authorizationGrantRequest);
        ResponseEntity<OAuth2AccessTokenResponse> response = getResponse(request);

        response.getBody();
        return null;
    }

    public ResponseEntity<OAuth2AccessTokenResponse> getResponse(RequestEntity<?> request) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(request, OAuth2AccessTokenResponse.class);
        } catch (RestClientException e){
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }





}
