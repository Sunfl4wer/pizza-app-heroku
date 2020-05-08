package com.pycogroup.pizza.product.common;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ErrorResponseBody {

  @Getter
  @Setter
  private LocalDateTime timestamp;

  @Getter
  @Setter
  private HttpStatus status;

  @Getter
  @Setter
  private int code;

  @Getter
  @Setter
  private String message;

  @Getter
  @Setter
  private String reason;

  @Getter
  @Setter
  private LinkedHashMap<String , Object> where;

  @Getter
  @Setter
  private LinkedHashMap<String , Object> when;
}
