package com.pycogroup.pizza.product.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.pycogroup.pizza.product.dto.ProductCardNotPizzaDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ProductCard {

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
  private Pricing pricing;

  @Getter
  @Setter
  private AdditionalOption additionalOption;

  @Getter
  @Setter
  private Category category;

  public Object toDto() {
    if (this.category != Category.PIZZA) {
      return ProductCardNotPizzaDto.builder()
                                    .id(this.getId())
                                    .name(this.getName())
                                    .description(this.getDescription())
                                    .imageURL(this.getImageURL())
                                    .pricing(this.getPricing().getPrice().get(0))
                                    .category(this.getCategory())
                                    
             .build();
    } else {
      return this;
    }
  }

  public static ArrayList<Object> toDtoList(Object listProductCards) {
    ArrayList<Object> listNotPizzaDto = new ArrayList<Object>();
    if (listProductCards instanceof List<?>) {
      int size = ((List<?>)listProductCards).size();
      for (int i=0; i < size; i++) {
        if (((List<?>)listProductCards).get(i) instanceof ProductCard) {
          ProductCard productCard = (ProductCard) ((List<?>)listProductCards).get(i);
          listNotPizzaDto.add(productCard.toDto());
        }
      }
    }
    return listNotPizzaDto;
  }
}
