package com.arh.logging.arhlogging.exception;

import static java.util.Optional.ofNullable;

import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
  INTERNAL_ERROR("WCP9999", "Internal Error", HttpStatus.INTERNAL_SERVER_ERROR),
  ACCOUNT_NOT_FOUND("WCP0001", "Account not found",HttpStatus.NOT_FOUND),
  CARD_MUST_BE_INACTIVE("WCP0002", "Card must be inactive", HttpStatus.PRECONDITION_FAILED),

  CARD_NOT_FOUND("WCP0003", "Card not found", HttpStatus.NOT_FOUND),
  INTERNAL_CARD_NOT_FOUND("WCI0004", "Internal card not found", HttpStatus.PRECONDITION_FAILED),

  ASYNC_CARD_NOT_FOUND("WCA0005", "Async card not found", HttpStatus.INTERNAL_SERVER_ERROR);

  // prefixs:
  //      public - WCP - requests de app e web
  //      internal - WCI - requests de sistemas internos (salesforce, outros times, etc)
  //      async - WCA - fluxo assíncrono de mensageria

  private final String code;
  private final String message;
  private final HttpStatus statusCode;

  public static ErrorCode of(final String code) {
    return ofNullable(code)
        .flatMap(c -> Stream.of(values()).filter(error -> error.getCode().equals(c)).findFirst())
        .orElse(null);
  }
}
