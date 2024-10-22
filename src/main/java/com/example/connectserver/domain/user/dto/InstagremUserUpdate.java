package com.example.connectserver.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstagremUserUpdate {
    @JsonProperty("insta_id")
    private String instaId;
    private String username;
    private String password;
}
