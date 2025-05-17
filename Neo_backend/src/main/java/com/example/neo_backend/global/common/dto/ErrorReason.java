package com.example.neo_backend.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorReason {
    private final Integer status;
    private final String code;
    private final String reason;
    private final String info;
}