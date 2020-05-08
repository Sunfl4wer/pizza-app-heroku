package com.pycogroup.pizza.product.common;

import lombok.Getter;

public enum Reason {

  BAD_PARAMS("Bad input params."), 
  BAD_PATH_VARIABLE("Bad path variable."),
  BAD_FIELD_VALUES("Bad field values.");

  @Getter
  private final String reason;
  
  Reason(String reason) {
    this.reason = reason;
  }
}
