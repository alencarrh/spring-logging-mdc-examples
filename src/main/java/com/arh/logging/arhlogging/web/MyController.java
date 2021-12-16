package com.arh.logging.arhlogging.web;

import java.util.Map;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/my")
public class MyController {

  @GetMapping()
  public Object my() {
    log.info("no params");
    return Map.of("param", "no-param");
  }

  @GetMapping("/sensitive/{param}")
  public Object mySensitiveParam(@PathVariable("param") final String param) {

    if ("error".equals(param)) {
      throw new SomeException();
    }
    return Map.of("param", param, "params", param);
  }

  @GetMapping("/{param}")
  public Object myParam(@PathVariable("param") final String param) {
    log.info("with param");
    return Map.of("param", param);
  }

  @GetMapping("/{param1}/{param2}")
  public Object myTwoParams(
      @PathVariable("param1") final String param1, @PathVariable("param2") final String param2) {
    log.info("with two params");
    return Map.of("param1", param1, "param2", param2);
  }

  @PostMapping("/request/")
  public Object myRequest(@RequestBody final MyRequest request) {
    log.info("my request");

    return Map.of("request", request.toString());
  }

  @Getter
  @Builder
  @EqualsAndHashCode
  @ToString
  static class MyRequest {
    private final String some;
    private final String value;
  }

  static class SomeException extends RuntimeException {}
}
