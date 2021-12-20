package com.arh.logging.arhlogging.interceptor;

import com.arh.logging.arhlogging.util.LoggerUtil;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class LoggerInterceptor extends OncePerRequestFilter {

  // pre controller request
  private static final String METHOD = "request_method";
  private static final String URI = "request_uri";
  private static final String PROTOCOL = "request_protocol";

  // pre controller headers
  private static final String SOURCE_IP = "request_ip";
  private static final String WALLET_ID = "x_wallet_id";

  // do not log all headers because tokens and sensitive headers, log only necessary/known ones
  private static final Map<String, String> loggableHeaders =
      Map.of(
          "X-FORWARDED-FOR", SOURCE_IP,
          "X-WALLET-ID", WALLET_ID);

  // pos controller response
  private static final String DURATION = "request_duration";
  private static final String STATUS = "request_status";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final long init = System.currentTimeMillis();
    LoggerUtil.put(METHOD, request.getMethod());
    LoggerUtil.put(URI, request.getRequestURI());
    LoggerUtil.put(PROTOCOL, request.getProtocol());
    loggableHeaders.forEach(
        (headerName, logName) -> LoggerUtil.put(logName, request.getHeader(headerName)));

    try {
      filterChain.doFilter(request, response);

    } finally {

      final long duration = System.currentTimeMillis() - init;
      LoggerUtil.put(DURATION, duration);
      LoggerUtil.put(STATUS, response.getStatus());

      log.info(
          "{} {} status={} time={}ms",
          request.getMethod(),
          request.getRequestURI(),
          response.getStatus(),
          duration);
      LoggerUtil.clear();
    }
  }
}
