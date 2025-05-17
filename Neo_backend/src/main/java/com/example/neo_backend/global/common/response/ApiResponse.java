package com.example.neo_backend.global.common.response;

import com.example.neo_backend.global.common.status.SuccessStatus;
import com.example.neo_backend.global.common.status.ErrorStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;


@Getter
@RequiredArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "pageInfo", "result"})
public class ApiResponse {
    private final Boolean isSuccess;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PageInfo pageInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object result;

    // 성공한 경우 응답 생성
    public static ResponseEntity<ApiResponse> onSuccess(SuccessStatus status, PageInfo pageInfo, Object result) {
        return new ResponseEntity<>(
                new ApiResponse(true, status.getCode(), status.getMessage(), pageInfo, result),
                status.getHttpStatus()
        );
    }

    // 성공 - 기본 응답
    public static ResponseEntity<ApiResponse> onSuccess(SuccessStatus status) {
        return onSuccess(status, null, null);
    }

    // 성공 - 데이터 포함
    public static ResponseEntity<ApiResponse> onSuccess(SuccessStatus status, Object result) {
        return onSuccess(status, null, result);
    }

    // 성공 - 페이지네이션 포함
    public static ResponseEntity<ApiResponse> onSuccess(SuccessStatus status, Page<?> page) {
        PageInfo pageInfo = new PageInfo(page.getNumber(), page.getSize(), page.hasNext(), page.getTotalElements(),
                page.getTotalPages());
        return onSuccess(status, pageInfo, page.getContent());
    }


    // 실패한 경우 응답 생성
    public static ResponseEntity<ApiResponse> onFailure(ErrorStatus error) {
        return new ResponseEntity<>(
                new ApiResponse(false, error.getCode(), error.getMessage(), null, null), error.getHttpStatus());
    }


    public static ResponseEntity<ApiResponse> onFailure(ErrorStatus error, String message) {
        return new ResponseEntity<>(new ApiResponse(false, error.getCode(), error.getMessage(), null, null), error.getHttpStatus());
    }
}