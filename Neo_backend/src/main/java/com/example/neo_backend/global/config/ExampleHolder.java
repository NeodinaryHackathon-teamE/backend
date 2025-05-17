package com.example.neo_backend.global.config;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExampleHolder {
    private final Example holder;
    private final Integer httpStatus; // 숫자 (예: 400)
    private final String name;        // "AUTH4001"
}
