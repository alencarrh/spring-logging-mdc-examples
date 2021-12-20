package com.arh.logging.arhlogging.util;

import static java.util.Optional.ofNullable;

import java.util.Map;
import org.slf4j.MDC;

public final class LoggerUtil {

  public static void clear() {
    MDC.clear();
  }

  public static void putAll(final Map<String, String> attrs) {
    ofNullable(attrs).ifPresent(attr -> attr.forEach(LoggerUtil::put));
  }

  public static void put(final String key, final Object value) {
    ofNullable(value).map(Object::toString).ifPresent(v -> MDC.put(key, v));
  }
}
