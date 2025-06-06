package com.example.neo_backend.global.common.exception;

import com.example.neo_backend.global.common.dto.ErrorReason;

public interface BaseErrorCode {
    ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}