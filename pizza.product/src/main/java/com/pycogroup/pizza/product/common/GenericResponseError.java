package com.pycogroup.pizza.product.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GenericResponseError extends GenericResponse {

  public GenericResponseError(Object data) {
    super(data);
  }

  @Override
  @JsonProperty("errors")
  @JsonIgnoreProperties("data")
  public Object getData() {
    return super.getData();
  }
}
