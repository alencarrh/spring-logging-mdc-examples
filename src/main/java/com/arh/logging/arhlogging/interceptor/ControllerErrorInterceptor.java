package com.arh.logging.arhlogging.interceptor;

import com.arh.logging.arhlogging.exception.ErrorMessage;
import com.arh.logging.arhlogging.exception.example42.AbstractError42Exception;
import com.arh.logging.arhlogging.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerErrorInterceptor {
  @ExceptionHandler(AbstractError42Exception.class)
  public ResponseEntity<ErrorMessage> abstractError42Exception(AbstractError42Exception e) {
    ErrorMessage message =
        ErrorMessage.builder()
            .error(e.getErrorCode().getCode())
            .message(e.getMessage())
            .detail(e.getDetails())
            .build();
    LoggerUtil.put("response_exception", e.getClass().getSimpleName());

    return new ResponseEntity<>(message, e.getErrorCode().getStatusCode());
  }
}
