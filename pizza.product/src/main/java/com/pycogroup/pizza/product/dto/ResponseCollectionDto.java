package com.pycogroup.pizza.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ResponseCollectionDto {

  @Setter
  @Getter
  private int code;

  @Getter
  @Setter
  private Object data;

  @Setter
  @Getter
  private Object meta;

  @Getter
  @Setter
  private Object links;
}
