package com.pycogroup.pizza.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pricing {

  private List<Size> size;
  private List<Integer> price;
  private int selected;

  public Pricing (List<Size> size, List<Integer> price, int selected) {
    
    this.size = size;
    this.price = price;
    this.selected = selected;
  }
  
}
