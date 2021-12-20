package com.arh.logging.arhlogging.exception.example42;

import com.arh.logging.arhlogging.exception.ErrorCode;
import com.arh.logging.arhlogging.util.LoggerUtil;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public abstract class AbstractError42Exception extends RuntimeException {

  private final Map<String, String> details;

  public AbstractError42Exception(final ErrorCode error) {
    this(error, null, Collections.emptyMap());
  }

  public AbstractError42Exception(final ErrorCode error, final Throwable throwable) {
    this(error, throwable, Collections.emptyMap());
  }

  public AbstractError42Exception(final ErrorCode error, final Map<String, String> attrs) {
    this(error, null, attrs);
  }

  public AbstractError42Exception(
      final ErrorCode error, final Throwable throwable, final Map<String, String> attrs) {
    super(error.getMessage(), throwable);
    details = attrs;
    LoggerUtil.putAll(attrs);
  }

  public abstract ErrorCode getErrorCode();
}
