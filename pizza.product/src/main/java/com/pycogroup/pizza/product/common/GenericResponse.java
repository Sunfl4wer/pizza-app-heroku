package com.pycogroup.pizza.product.common;

import lombok.Getter;
import lombok.Setter;

public class GenericResponse {

  @Getter
  @Setter
  private Object data;

  public GenericResponse(Object data) {
    this.data = data;
  }
}
