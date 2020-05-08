package com.pycogroup.pizza.authentication.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pycogroup.pizza.model.ERole;
import com.pycogroup.pizza.model.Role;

public interface RoleRepository extends MongoRepository<Role , String>{

  Optional<Role> findByName(ERole name);
}
