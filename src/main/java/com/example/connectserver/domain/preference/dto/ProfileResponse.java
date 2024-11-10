package com.example.connectserver.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String instagramUsername;
    private String intro;
    private List<PreferenceItem> preferences;
}
