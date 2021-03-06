package com.tutor.tutorlab.config.controlleradvice;

import com.tutor.tutorlab.config.response.ErrorCode;
import com.tutor.tutorlab.config.response.ErrorResponse;
import com.tutor.tutorlab.config.response.exception.EntityNotFoundException;
import com.tutor.tutorlab.config.response.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException e, HttpServletRequest req) {
        log.error("===================== RuntimeException Handling =====================");
        e.printStackTrace();
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "서버 오류가 발생하였습니다.", Collections.singletonList(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, e.getMessage());
        // return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
        // return new ErrorResponse(ErrorCode.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse handlerBindException(BindException e, HttpServletRequest req) {
        log.error("===================== BindException Handling =====================");
        e.printStackTrace();
        return getErrorResponse(e.getBindingResult(), HttpStatus.BAD_REQUEST, "유효하지 않는 값이 있습니다.");
    }

    private ErrorResponse getErrorResponse(BindingResult bindingResult, HttpStatus httpStatus, String defaultMessage) {
        List<String> errorMessages = Optional.ofNullable(bindingResult.getAllErrors()).orElse(Collections.emptyList())
                .stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        return ErrorResponse.of(httpStatus.value(), defaultMessage, errorMessages);
    }
}
