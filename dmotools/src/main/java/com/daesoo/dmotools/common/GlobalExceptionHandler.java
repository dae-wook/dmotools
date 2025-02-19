package com.daesoo.dmotools.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.daesoo.dmotools.common.dto.ErrorResponseDto;
import com.daesoo.dmotools.common.dto.ErrorType;
import com.daesoo.dmotools.common.dto.ResponseDto;
import com.daesoo.dmotools.common.exception.UnauthorizedException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseDto<ErrorResponseDto> handleException(Exception ex) {
//    	log.error(" occurred: {}", ex.getMessage());
//    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.EXCEPTION, ex.getMessage());
//        return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDto);
//    }
	
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorResponseDto> handle(Exception ex) {
    	log.error(" occurred: {}", ex.getMessage());
    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.ILLEGAL_ARGUMENT_EXCEPTION, ex.getMessage());
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, errorResponseDto);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDto<ErrorResponseDto> handleAuthenticationException(UnauthorizedException ex) {
    	log.error(" occurred: {}", ex.getMessage());
    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.UNATHORIZED_EXCEPTION, ex.getMessage());
        return ResponseDto.fail(HttpStatus.UNAUTHORIZED, errorResponseDto);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    	log.error(" occurred: {}", ex.getMessage());
    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.VALIDATION_EXCEPTION, ex.getAllErrors().get(0).getDefaultMessage());
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, errorResponseDto);
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
    	log.error(" occurred: {}", ex.getMessage());
    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.ENTITY_NOT_FOUND_EXCEPTION, ex.getMessage());
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, errorResponseDto);
    }
    
    @ExceptionHandler({IOException.class, HttpMessageNotWritableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorResponseDto> handleIOException(IOException ex) {
    	log.error(" occurred: {}", ex.getMessage());
    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.ENTITY_NOT_FOUND_EXCEPTION, ex.getMessage());
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, errorResponseDto);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorResponseDto> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    	log.error(" occurred: {}", ex.getMessage());
    	ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorType.ILLEGAL_ARGUMENT_EXCEPTION,
    			String.format("잘못된 값이 입력되었습니다. 파라미터 '%s': '%s'는 허용되지 않습니다.",
    	                ex.getName(), ex.getValue()));
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, errorResponseDto);
    }
}
