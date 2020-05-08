package com.pycogroup.pizza.model;

public enum Category {

  PIZZA,
  SIDE_DISH,
  DRINK,
  DESSERT;

  public static boolean isCategory(String string) {
    for(Category category : Category.values()) {
      if (category.name().equals(string)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isCategory(Category category) {
    for(Category cat : Category.values()) {
      if (cat.equals(category)) {
        return true;
      }
    }
    return false;
  }
}
