package com.daesoo.dmotools.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponseDto {
	ErrorType errorType;
    String errorCode;
    String errorMessage;

    public static ErrorResponseDto of(ErrorType errorType, String errorMessage) {
        return ErrorResponseDto.builder()
        		.errorType(errorType)
                .errorCode(errorType.getCode())
                .errorMessage(errorMessage)
                .build();
    }
}