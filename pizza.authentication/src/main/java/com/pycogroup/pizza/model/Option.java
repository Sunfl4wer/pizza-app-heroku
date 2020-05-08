package com.pycogroup.pizza.model;

public enum Option {

  NONE,
  EXTRA_CHEESE,
  DOUBLE_CHEESE,
  TRIPLE_CHEESE;

  public static boolean isOption(String option) {
    for(Option Option : Option.values()) {
      if (Option.name().equals(option)) {
        return true;
      }
    }
    return false;
  }
}
