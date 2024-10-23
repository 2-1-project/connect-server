package com.example.connectserver.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstagramUserUpdate {
    @JsonProperty("insta_id")
    private String instaId;
    private String username;
    private String password;
}
