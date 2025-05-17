package com.example.neo_backend.global.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    // 공통 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4001", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4003", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4004", "페이지를 찾을 수 없습니다."),

    // 인증 관련 에러
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "AUTH4001", "중복된 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "AUTH4002", "해당하는 유저가 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH4003", "비밀번호가 일치하지 않습니다."),

    // 제보글 관련 에러
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "POST4001", "유효하지 않은 카테고리입니다."),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4002", "작성된 글이 없습니다."),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALID4001", "입력값이 올바르지 않습니다."),
    INVALID_KEYWORD(HttpStatus.BAD_REQUEST, "SEARCH4001", "검색어는 공백일 수 없습니다."),
    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "PAGENATION4001", "page는 음수일 수 없고, size는 100 이하의 양수여야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessageOrDefault(String overrideMessage) {
        return Optional.ofNullable(overrideMessage)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.message);
    }
}
