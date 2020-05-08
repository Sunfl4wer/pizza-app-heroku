package com.pycogroup.pizza.product.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pycogroup.pizza.product.common.ErrorResponseBody;
import com.pycogroup.pizza.product.common.GenericResponse;
import com.pycogroup.pizza.product.common.GenericResponseCollection;
import com.pycogroup.pizza.product.common.GenericResponseError;
import com.pycogroup.pizza.product.common.LogExecutionStatus;
import com.pycogroup.pizza.product.common.Message;
import com.pycogroup.pizza.product.common.MetaForCollection;
import com.pycogroup.pizza.product.common.Pagination;
import com.pycogroup.pizza.product.common.Reason;
import com.pycogroup.pizza.product.model.AdditionalOption;
import com.pycogroup.pizza.product.model.Category;
import com.pycogroup.pizza.product.model.Pricing;
import com.pycogroup.pizza.product.model.Product;
import com.pycogroup.pizza.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  ProductRepository productRepository;

  @Override
  @LogExecutionStatus
  public GenericResponse createProduct(Product product) {
    Category category = product.getCategory();
    if (category != Category.PIZZA) {  
      Pricing pricing = new Pricing(product.getPricing().getSize(),
                                    product.getPricing().getPrice(),
                                    0);
      product.setPricing(pricing);
      product.setAdditionalOption(null);
      Product createdProduct = productRepository.save(product);
      return new GenericResponse(createdProduct.toDto());
    } else {       
      Pricing pricing = new Pricing(product.getPricing().getSize(),
                                    product.getPricing().getPrice(),
                                    0);
      product.setPricing(pricing);
      AdditionalOption option = new AdditionalOption(product.getAdditionalOption().getOption(),
                                                     product.getAdditionalOption().getPrice(),
                                                     0);
      product.setAdditionalOption(option);
      Product createdProduct = productRepository.save(product);
      return new GenericResponse(createdProduct.toDto());
    }
  }
    
  @Override
  @LogExecutionStatus
  public GenericResponse createBulk(ArrayList<Product> products) {
    List<Product> createdProducts = productRepository.saveAll(products);
    return new GenericResponse(Product.toDtoList(createdProducts));
  }

  @Override
  @LogExecutionStatus
  public GenericResponse updateProduct(String id, Product product) {
    Optional<Product> receivedObject = productRepository.findById(id);
    if (!receivedObject.isPresent()) {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("id", id);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("return", "null");
      return new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.NOT_FOUND)
                                              .code(HttpStatus.NOT_FOUND.value())
                                              .message(Message.DOCUMENT_NOT_EXIST.getMessage())
                                              .reason(Reason.BAD_PATH_VARIABLE.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
      
    } else {
      Category category = product.getCategory();
      Product updatedProduct = receivedObject.get();
      updatedProduct.setName(product.getName());
      updatedProduct.setImageURL(product.getImageURL());
      updatedProduct.setDescription(product.getDescription());
      updatedProduct.setIngredients(product.getIngredients());
      updatedProduct.setServingSize(product.getServingSize());
      updatedProduct.setCategory(product.getCategory());
      if (category != Category.PIZZA) {  
        Pricing pricing = new Pricing(product.getPricing().getSize(),
                                      product.getPricing().getPrice(),
                                      0);
        product.setPricing(pricing);
        product.setAdditionalOption(null);
        updatedProduct.setPricing(product.getPricing());
        updatedProduct.setAdditionalOption(product.getAdditionalOption());
        updatedProduct = productRepository.save(updatedProduct);
        return new GenericResponse((updatedProduct.toDto()));
      } else {       
        Pricing pricing = new Pricing(product.getPricing().getSize(),
                                      product.getPricing().getPrice(),
                                      0);
        product.setPricing(pricing);
        AdditionalOption option = new AdditionalOption(product.getAdditionalOption().getOption(),
                                                          product.getAdditionalOption().getPrice(),
                                                          0);
        product.setAdditionalOption(option);
        updatedProduct.setPricing(product.getPricing());
        updatedProduct.setAdditionalOption(product.getAdditionalOption());
        updatedProduct = productRepository.save(updatedProduct);
        return new GenericResponse((updatedProduct.toDto()));
      }
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse deleteProduct(String id) {
    Optional<?> receivedObject = productRepository.findById(id);
    GenericResponse response = null;
    if(!receivedObject.isPresent()) { 
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("id", id);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("return", "null");
      response =  new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.NOT_FOUND)
                                              .code(HttpStatus.NOT_FOUND.value())
                                              .message(Message.DOCUMENT_NOT_EXIST.getMessage())
                                              .reason(Reason.BAD_PATH_VARIABLE.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
    } else {
      if (receivedObject.get() instanceof Product) {
        Product product = (Product) receivedObject.get();
        productRepository.delete(product);
      }
      response =  new GenericResponse(null);
    }
    return response;
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getProductById(String productId) {
    
    Optional<Product> receivedObject = productRepository.findById(productId);
    if (!receivedObject.isPresent()) { 
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("id", productId);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("return", "null");
      return new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.NOT_FOUND)
                                              .code(HttpStatus.NOT_FOUND.value())
                                              .message(Message.DOCUMENT_NOT_EXIST.getMessage())
                                              .reason(Reason.BAD_PATH_VARIABLE.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
    } else {
      return new GenericResponse(((Product) receivedObject.get()).toDto());
    }
  }
  
  @Override 
  @LogExecutionStatus
  public GenericResponseCollection getProductCardsByCategoryWithPagination(Category category, int size, int page) {
    if (size == 0) size = 5;
    if (page == 0) page = 1;
    Pageable pageable = PageRequest.of(page-1, size);
    Page<Product> products = productRepository.findByCategory(category,pageable);
    ArrayList<Object> productCards = (ArrayList<Object>)Product.toProductCardList(products.getContent());
    return new GenericResponseCollection(
                       productCards,//data 
                       MetaForCollection.builder()//meta 
                       .pagination(new Pagination(size,page,products.getTotalPages(),products.getTotalElements()))
                       .build());
  }

  @Override
  @LogExecutionStatus
  public GenericResponseCollection getAllProductCardsWithPagination(int size, int page) {
    if (size == 0) size = 5;
    if (page == 0) page = 1;
    Pageable pageable = PageRequest.of(page-1, size);
    Page<Product> products = productRepository.findAll(pageable);
    ArrayList<Object> productCards = Product.toProductCardList(products.getContent());
    return new GenericResponseCollection(productCards,
                   MetaForCollection.builder()
                     .pagination(new Pagination(size,page,products.getTotalPages(),products.getTotalElements()))
                     .build());
  }
}
