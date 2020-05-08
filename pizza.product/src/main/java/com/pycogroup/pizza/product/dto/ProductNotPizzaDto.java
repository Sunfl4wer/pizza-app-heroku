package com.pycogroup.pizza.product.dto;

import com.pycogroup.pizza.product.model.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ProductNotPizzaDto {

  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String imageURL;
  
  @Getter
  @Setter
  private String description;

  @Getter
  @Setter
  private String ingredients;
 
  @Getter
  @Setter
  private int pricing;

  @Getter
  @Setter
  private Category category;
}
