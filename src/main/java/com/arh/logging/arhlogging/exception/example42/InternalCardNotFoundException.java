package com.arh.logging.arhlogging.exception.example42;

import com.arh.logging.arhlogging.exception.ErrorCode;
import java.util.Map;
import lombok.Getter;

@Getter
public class InternalCardNotFoundException extends AbstractError42Exception {

  private static final ErrorCode errorCode = ErrorCode.INTERNAL_CARD_NOT_FOUND;

  public InternalCardNotFoundException() {
    super(errorCode);
  }

  public InternalCardNotFoundException(final Throwable throwable) {
    super(errorCode, throwable);
  }

  public InternalCardNotFoundException(final Map<String, String> attrs) {
    super(errorCode, attrs);
  }

  public InternalCardNotFoundException(final Throwable throwable, final Map<String, String> attrs) {
    super(errorCode, throwable, attrs);
  }

  @Override
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
