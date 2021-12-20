package com.arh.logging.arhlogging.request;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class MyRequest implements Serializable {
  private final String value;
}
