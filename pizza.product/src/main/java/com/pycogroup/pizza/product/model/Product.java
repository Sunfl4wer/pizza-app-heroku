package com.pycogroup.pizza.product.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.pycogroup.pizza.product.dto.ProductNotPizzaDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "products")
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
  private String servingSize;

  @Getter
  @Setter
  private Pricing pricing;

  @Getter
  @Setter
  private AdditionalOption additionalOption;

  @Getter
  @Setter
  private Category category;

  public ProductCard toProductCard() {
    ProductCard productCard = ProductCard.builder()
                                .id(this.getId())
                                .name(this.getName())
                                .description(this.getDescription())
                                .imageURL(this.getImageURL())
                                .pricing(this.getPricing())
                                .additionalOption(this.getAdditionalOption())
                                .category(this.getCategory())
                              .build();
    return productCard;
  }
  
  public Object toDto() {
    if (this.category != Category.PIZZA) {
      return ProductNotPizzaDto.builder()
                                    .id(this.getId())
                                    .name(this.getName())
                                    .description(this.getDescription())
                                    .ingredients(this.getIngredients())
                                    .imageURL(this.getImageURL())
                                    .pricing(this.getPricing().getPrice().get(0))
                                    .category(this.getCategory())
             .build();
    } else {
      return this;
    }
  }

  public static ArrayList<Object> toProductCardList(Object products) {
    ArrayList<Object> productCards = new ArrayList<Object>();
    if (products instanceof List<?>) {
      int size = ((List<?>)products).size();
      for (int i=0; i < size; i++) {
        if (((List<?>)products).get(i) instanceof Product) {
          Product product = (Product) ((List<?>)products).get(i);
          productCards.add((product.toProductCard()).toDto());
        }
      }
    }
    return productCards;
  }

  public static ArrayList<Object> toDtoList(Object listProducts) {
    ArrayList<Object> listNotPizzaDto = new ArrayList<Object>();
    if (listProducts instanceof List<?>) {
      int size = ((List<?>)listProducts).size();
      for (int i=0; i < size; i++) {
        if (((List<?>)listProducts).get(i) instanceof Product) {
          Product product = (Product) ((List<?>)listProducts).get(i);
          listNotPizzaDto.add(product.toDto());
        }
      }
    }
    return listNotPizzaDto;
  }
}
