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

/**
 * http://kibana01.olxbr.io/app/kibana#/discover?_g=(refreshInterval:(pause:!t,value:0),time:(from:now-12h,mode:quick,to:now))&_a=(columns:!(application,requestEndpoint,requestHeaders.origin,requestHeaders.accept,requestHeaders.accept-encoding,requestHeaders.accept-language,requestHeaders.host,requestHeaders.referer,requestHeaders.request-id,requestHeaders.sec-fetch-dest,requestHeaders.sec-fetch-mode,requestHeaders.sec-fetch-site,requestHeaders.user-agent,requestHeaders.x-b3-parentspanid,requestHeaders.x-b3-sampled,requestHeaders.x-b3-spanid,requestHeaders.x-forwarded-olx-office,requestHeaders.x-forwarded-for,requestHeaders.x-forwarded-host,requestHeaders.x-forwarded-port,requestHeaders.x-forwarded-proto,requestHeaders.x-b3-traceid,requestHeaders.x-olx-team-key,requestHeaders.x-olxbr-account-accountid,requestHeaders.x-olxbr-account-email,requestHeaders.x-olxbr-account-phone,requestHeaders.x-olxbr-account-phoneverified,requestHeaders.x-original-forwarded-for,requestHeaders.x-real-ip,requestHeaders.x-request-id,requestHeaders.x-scheme,requestHeaders.x-source-client-ip,requestMethod,requestParameters,requestProtocol,responseData,responseSize,responseStatus,requestHeaders.service-key,requestHeaders.x-amzn-trace-id,X-B3-TraceId),filters:!(('$state':(store:appState),meta:(alias:!n,disabled:!t,index:ef231fe0-2316-11e9-b7e9-fb1d7a8f03c1,key:X-B3-TraceId,negate:!f,params:(query:'186630cfdf078421',type:phrase),type:phrase,value:'186630cfdf078421'),query:(match:(X-B3-TraceId:(query:'186630cfdf078421',type:phrase)))),('$state':(store:appState),meta:(alias:!n,disabled:!t,index:ef231fe0-2316-11e9-b7e9-fb1d7a8f03c1,key:X-B3-TraceId,negate:!f,params:(query:c5da2750895d190c,type:phrase),type:phrase,value:c5da2750895d190c),query:(match:(X-B3-TraceId:(query:c5da2750895d190c,type:phrase)))),('$state':(store:appState),meta:(alias:!n,disabled:!t,index:ef231fe0-2316-11e9-b7e9-fb1d7a8f03c1,key:application,negate:!f,params:!(wallet-api),type:phrases,value:wallet-api),query:(bool:(minimum_should_match:1,should:!((match_phrase:(application:wallet-api)))))),('$state':(store:appState),meta:(alias:!n,disabled:!f,index:ef231fe0-2316-11e9-b7e9-fb1d7a8f03c1,key:X-B3-TraceId,negate:!f,params:(query:'48a4a002f781d695',type:phrase),type:phrase,value:'48a4a002f781d695'),query:(match:(X-B3-TraceId:(query:'48a4a002f781d695',type:phrase))))),index:ef231fe0-2316-11e9-b7e9-fb1d7a8f03c1,interval:auto,query:(language:lucene,query:''),sort:!('@timestamp',asc))
 */
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
