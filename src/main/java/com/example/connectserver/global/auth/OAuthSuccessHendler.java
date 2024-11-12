package com.example.connectserver.global.auth;

import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.service.UserManagementService;
import com.example.connectserver.global.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHendler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserManagementService userManagementService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String id = oAuth2User.getAttribute("id");
        String token = jwtProvider.OAuthTokenGenerator(id);
        response.addCookie(makeCookie("Authorization",token));
        System.out.println("OAuthSuccess");
        response.setStatus(200); // 정상 수신으로 설정
    }

    public Cookie makeCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
