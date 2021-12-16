package com.arh.logging.arhlogging.util;

import static java.util.Optional.ofNullable;

import org.slf4j.MDC;

public final class LoggerUtil {

  public static void clear() {
    MDC.clear();
  }

  public static void put(final String key, final Object value) {
    ofNullable(value).map(Object::toString).ifPresent(v -> MDC.put(key, v));
  }
}
