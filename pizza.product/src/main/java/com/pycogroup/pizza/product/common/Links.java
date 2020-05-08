package com.pycogroup.pizza.product.common;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Links {

  @Getter
  @Setter
  private ArrayList<LinkEntity> links;

  public Links() {
    this.links = new ArrayList<LinkEntity>();
  }

  public boolean add(final LinkEntity hateoasEntity) {
    return this.links.add(hateoasEntity);
  }
}
