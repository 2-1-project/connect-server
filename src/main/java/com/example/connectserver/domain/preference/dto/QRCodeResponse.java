package com.example.connectserver.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QRCodeResponse {
    private String url;
    private byte[] imageData;
}
