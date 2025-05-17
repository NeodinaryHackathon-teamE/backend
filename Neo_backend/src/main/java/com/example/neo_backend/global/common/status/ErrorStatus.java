package com.example.neo_backend.global.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4001", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4003", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4004", "페이지를 찾을 수 없습니다."),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALID4001", "입력값이 올바르지 않습니다."),
    INVALID_KEYWORD(HttpStatus.BAD_REQUEST, "SEARCH4001", "검색어는 공백일 수 없습니다."),
    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "PAGENATION4001", "page는 음수일 수 없고, size는 100 이하의 양수여야 합니다."),

    _PIN_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4042", "해당 핀을 찾을 수 없습니다."),
    _POST_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "POST5000", "게시글 처리 중 서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessageOrDefault(String overrideMessage) {
        return Optional.ofNullable(overrideMessage)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.message);
    }
}
