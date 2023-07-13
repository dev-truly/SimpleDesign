package com.simpledesign.ndms.common;

import com.simpledesign.ndms.dto.HeaderDto;
import com.simpledesign.ndms.dto.SetDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> IndentRequestParameterExceptions(
            MethodArgumentNotValidException ex) {
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            log.error("[{}] {} : {}", error.getObjectName(), ((FieldError) error).getField(), error.getDefaultMessage());
        });
        SetDto errors = new SetDto();
        ErrorCode errorCode = ErrorCode.IndentRequestParameterError;
        errors.setHeader(HeaderDto.fromErrorCode(errorCode));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TypeMismatchException.class, NumberFormatException.class, ConstraintViolationException.class})
    public ResponseEntity<SetDto> invalidRequestException(Exception ex) {
        SetDto errors = new SetDto();
        ErrorCode errorCode = ErrorCode.InvalidRequestParameterError;
        errors.setHeader(HeaderDto.fromErrorCode(errorCode));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<SetDto> requestBindException(BindException ex) {
        AtomicBoolean isRequried = new AtomicBoolean(false);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            if (msg.matches(".*(empty|null)$")) {
                isRequried.set(true);
            }
            log.error("[{}] {} : {}", error.getObjectName(), ((FieldError) error).getField(), error.getDefaultMessage());
        });

        SetDto errors = new SetDto();
        ErrorCode errorCode = (isRequried.get()) ? ErrorCode.IndentRequestParameterError : ErrorCode.InvalidRequestParameterError;
        errors.setHeader(HeaderDto.fromErrorCode(errorCode));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

