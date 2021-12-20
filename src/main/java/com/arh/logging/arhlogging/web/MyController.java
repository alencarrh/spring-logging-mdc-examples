package com.arh.logging.arhlogging.web;

import com.arh.logging.arhlogging.exception.example42.CardNotFoundException;
import com.arh.logging.arhlogging.exception.example42.InternalCardNotFoundException;
import com.arh.logging.arhlogging.exception.example42.InternalErrorException;
import com.arh.logging.arhlogging.request.MyRequest;
import com.arh.logging.arhlogging.util.LoggerUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class MyController {

  @GetMapping("/my")
  public Object my() {
    log.info("no params");
    return Map.of("param", "no-param");
  }

  @GetMapping("/my/{param}")
  public Object myParam(@PathVariable("param") final String param) {
    log.info("with param");
    if (param.equals("421")) {
      throw new CardNotFoundException(Map.of("cardId", "card-id-1234567789", "param", param));
    }

    if (param.equals("422")) {
      throw new InternalCardNotFoundException(Map.of("cardId", "card-id-1234567789"));
    }

    if (param.equals("423")) {
      throw new InternalErrorException(Map.of("some", "123"));
    }

    if (param.equals("424")) {
      InternalErrorException e = new InternalErrorException(Map.of("some", "123"));
      e.getDetails().put("detail", "here");
      throw e;
    }

    return Map.of("param", param);
  }

  @GetMapping("/my/{param1}/{param2}")
  public Object myTwoParams(
      @PathVariable("param1") final String param1, @PathVariable("param2") final String param2) {
    log.info("with two params");
    return Map.of("param1", param1, "param2", param2);
  }

  //  @LogParam("request.value")
  @PostMapping("/post/request")
  public Object myRequest(@RequestBody final MyRequest request) {
    log.info("my request");
    log.info("teste2");
    LoggerUtil.put("request.value", request.getValue());
    if (request.getValue().equals("42")) {
      throw new CardNotFoundException(Map.of("some", "123"));
    }

    log.info("abc");
    return Map.of("request", request.toString());
  }
}
