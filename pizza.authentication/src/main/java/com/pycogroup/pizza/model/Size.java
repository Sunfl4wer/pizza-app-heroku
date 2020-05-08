package com.pycogroup.pizza.model;

public enum Size {

  SMALL,
  MEDIUM,
  LARGE,
  ONE_SIZE;

  public static boolean isSize(String size) {
    for(Size Size : Size.values()) {
      if (Size.name().equals(size)) {
        return true;
      }
    }
    return false;
  }
}
