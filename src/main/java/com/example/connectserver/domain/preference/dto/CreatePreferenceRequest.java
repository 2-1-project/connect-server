package com.example.connectserver.domain.preference.dto;

import lombok.Data;

@Data
public class CreatePreferenceRequest {
    private Long userId;
    private String question;
    private String answer;
}
