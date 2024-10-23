package com.example.connectserver.global.auth;

import com.example.connectserver.domain.user.dto.CustomUserDetails;
import com.example.connectserver.global.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSeccessHendler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        String token = jwtProvider.TokenGenerator(userDetails);
        response.addHeader("Authorization", "Bearer " + token);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
