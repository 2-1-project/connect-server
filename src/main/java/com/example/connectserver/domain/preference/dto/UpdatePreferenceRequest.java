package com.example.connectserver.domain.preference.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdatePreferenceRequest {
    private String name;
    private String intro;
    private List<PreferenceItem> list;
    private String existingQrCodeUrl;
}
