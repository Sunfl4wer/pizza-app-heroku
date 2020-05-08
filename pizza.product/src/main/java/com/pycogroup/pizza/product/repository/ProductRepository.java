package com.pycogroup.pizza.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.pycogroup.pizza.product.model.Category;
import com.pycogroup.pizza.product.model.Product;

public interface ProductRepository extends MongoRepository<Product , String>{

  @Query("{'Category' : ?0}")
  Page<Product> findByCategory(Category category, Pageable pageable);

  Page<Product> findAll(Pageable pageable);

  Optional<Product> findById(String id);
}
