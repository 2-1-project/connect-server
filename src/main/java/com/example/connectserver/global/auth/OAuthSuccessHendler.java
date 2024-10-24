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
        UserEntity user = userManagementService.getUserByInstaId(id);
        if (user.getUsername() == null) {
            System.out.println("ggg");
            response.sendRedirect("https://localhost:8443/test_null"); // 정보 입력 페이지로 이동.
        }else {
            System.out.println("OAuthSuccess");
            response.sendRedirect("http://localhost:3000"); // 매인페이지로 이동
        }
    }

    public Cookie makeCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
