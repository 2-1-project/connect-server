package com.example.connectserver.global.config;

import com.example.connectserver.domain.user.service.CustomOauth2UserService;
import com.example.connectserver.domain.user.service.UserManagementService;
import com.example.connectserver.global.auth.CustomOAuth2AccessTokenResponseClient;
import com.example.connectserver.global.auth.InstagramOAuthService;
import com.example.connectserver.global.auth.OAuthSuccessHendler;
import com.example.connectserver.global.filter.CorsFilter;
import com.example.connectserver.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtProvider jwtProvider;
    private final CustomOauth2UserService oauth2UserService;
    private final CustomOAuth2AccessTokenResponseClient oAuth2AccessTokenResponseClient;
    private final UserManagementService userManagementService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(new CorsFilter()))

                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests( request ->
                        request
                            .requestMatchers("/","/login/**","/test_null").permitAll()
                            .requestMatchers("/preferences/qr/{userId}").permitAll()
                            .requestMatchers("/preferences/{userId}").permitAll()
                            .requestMatchers("/preferences").permitAll()

                            .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(oauth ->
                        oauth
                                .successHandler(new OAuthSuccessHendler(jwtProvider,userManagementService))
                                .tokenEndpoint(t ->
                                            t.accessTokenResponseClient(oAuth2AccessTokenResponseClient)
                                        )
                                .userInfoEndpoint(usI -> usI.userService(oauth2UserService))
                );

        return http.build();
    }
}
