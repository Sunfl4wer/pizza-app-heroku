package com.pycogroup.pizza.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalOption {

  private List<Option> option;
  private List<Integer> price;
  private int selected;

  public AdditionalOption (List<Option> option, List<Integer> price, int selected) {
    this.option = option;
    this.price = price;
    this.selected = selected; 
  }
}
