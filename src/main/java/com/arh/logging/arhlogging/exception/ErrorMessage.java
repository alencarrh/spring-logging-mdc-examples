package com.arh.logging.arhlogging.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage {
  @NonNull private final String error;
  @NonNull private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final Map<String, String> detail;
}
