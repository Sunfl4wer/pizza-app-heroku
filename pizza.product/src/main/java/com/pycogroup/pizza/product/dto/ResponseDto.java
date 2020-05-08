package com.pycogroup.pizza.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ResponseDto {

  @Setter
  @Getter
  private int code;

  @Getter
  @Setter
  private Object data;

  @Getter
  @Setter
  private Object links;
}
