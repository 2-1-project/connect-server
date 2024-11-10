package com.example.connectserver.domain.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryPreferenceListResponse {
    private String name;
    private String intro;
}
