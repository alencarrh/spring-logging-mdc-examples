package com.arh.logging.arhlogging.exception.example42;

import com.arh.logging.arhlogging.exception.ErrorCode;
import java.util.Map;
import lombok.Getter;

@Getter
public class InternalErrorException extends AbstractError42Exception {

  private static final ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

  public InternalErrorException() {
    super(errorCode);
  }

  public InternalErrorException(final Throwable throwable) {
    super(errorCode, throwable);
  }

  public InternalErrorException(final Map<String, String> attrs) {
    super(errorCode, attrs);
  }

  public InternalErrorException(final Throwable throwable, final Map<String, String> attrs) {
    super(errorCode, throwable, attrs);
  }

  @Override
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
