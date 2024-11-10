package com.example.connectserver.global.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstagramAccessToken {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "user_id")
    private String userId;
}
