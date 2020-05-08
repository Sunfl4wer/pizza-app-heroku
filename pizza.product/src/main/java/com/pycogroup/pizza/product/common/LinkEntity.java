package com.pycogroup.pizza.product.common;

import java.net.URI;

import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LinkEntity {

  @Getter
  @Setter
  private URI href;
  
  @Getter
  @Setter
  private String relation;
  
  @Getter
  @Setter
  private RequestMethod method;
}
