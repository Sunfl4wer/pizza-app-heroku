package com.pycogroup.pizza.authentication.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pycogroup.pizza.model.User;

public class UserDetailsImpl implements UserDetails{


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String id;

  private String firstName;
  
  private String lastName;

  private String email;

  private String username;

  @JsonIgnore
  private String password;

  private String address;

  private String birthDate;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(String id, String firstName, String lastName, String phoneNumber, 
                         String email, String password, String address, String birthDate,
                         Collection<? extends GrantedAuthority> authorities) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = phoneNumber;
    this.email = email;
    this.password = password;
    this.address = address;
    this.birthDate = birthDate;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(), 
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(), 
        user.getAddress(),
        user.getBirthDate(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }
  
  public String getBirthDate() {
    return birthDate;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) object;
    return Objects.equals(id, user.id); 
  }
}
