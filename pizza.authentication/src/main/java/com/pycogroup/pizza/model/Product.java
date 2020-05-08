package com.pycogroup.pizza.model;

import java.util.List;

import org.springframework.data.annotation.Id;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Product {

  @Id
  @Getter
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
  private List<String> servingSize;

  @Getter
  @Setter
  private Pricing pricing;

  @Getter
  @Setter
  private AdditionalOption additionalOption;

  @Getter
  @Setter
  private Category category;

  @Getter
  @Setter
  private String productCardId;
}
