package com.example.neo_backend.global.common.exception;

import com.example.neo_backend.global.common.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpotItCodeException extends RuntimeException {
    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}