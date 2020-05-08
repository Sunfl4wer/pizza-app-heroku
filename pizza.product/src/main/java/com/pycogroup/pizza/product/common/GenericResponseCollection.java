package com.pycogroup.pizza.product.common;

import lombok.Getter;
import lombok.Setter;

public class GenericResponseCollection extends GenericResponse {

  @Getter
  @Setter
  private Object meta;

  public GenericResponseCollection(Object data, Object meta) {
    super(data);
    this.meta = meta;
  }

  @Override
  public Object getData() {
    return super.getData();
  }
}
