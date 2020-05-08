package com.pycogroup.pizza.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Document(collection="users")
@Builder
public class User {

  @Id
  @Getter
  private String id;

  @Setter
  @Getter
  private String firstName;

  @Setter
  @Getter
  private String lastName;

  @Setter
  @Getter
  @Email
  private String email;

  @Setter
  @Getter
  @NotBlank
  private String username;

  @Setter
  @Getter
  @NotBlank
  private String password;

  @Setter
  @Getter
  private String address;
  
  @Setter
  @Getter
  private String birthDate;

  @Getter
  @Setter
  @DBRef
  private Set<Role> roles = new HashSet<>();

}
