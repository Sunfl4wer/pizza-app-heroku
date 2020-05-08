package com.pycogroup.pizza.product.common;

import lombok.Getter;

public enum Message {

  NO_DOCUMENT_FOUND("No document found."),
  DOCUMENT_NOT_EXIST("Document not exist."), 
  BAD_REQUEST_BODY("Request body is invalid."),  
  DOCUMENT_EXISTED("Document existed."),  
  BAD_REQUEST_FORMAT("Bad request format");

  @Getter
  private final String message;
  
  Message(String message) {
    this.message = message;
  }
}
