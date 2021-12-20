package com.arh.logging.arhlogging.exception.example42;

import com.arh.logging.arhlogging.exception.ErrorCode;
import java.util.Map;
import lombok.Getter;

@Getter
public class CardNotFoundException extends AbstractError42Exception {

  private static final ErrorCode errorCode = ErrorCode.CARD_NOT_FOUND;

  public CardNotFoundException() {
    super(errorCode);
  }

  public CardNotFoundException(final Throwable throwable) {
    super(errorCode, throwable);
  }

  public CardNotFoundException(final Map<String, String> attrs) {
    super(errorCode, attrs);
  }

  public CardNotFoundException(final Throwable throwable, final Map<String, String> attrs) {
    super(errorCode, throwable, attrs);
  }

  @Override
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
