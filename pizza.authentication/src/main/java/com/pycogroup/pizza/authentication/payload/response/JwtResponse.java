package com.pycogroup.pizza.authentication.payload.response;

import java.util.List;

public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private String id;
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String address;
  private String birthDate;
  private List<String> roles;

  public JwtResponse(String accessToken, String id, String firstName, String lastName, String username,
      String email, String address, String birthDate, List<String> roles) {
    super();
    this.token = accessToken;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.address = address;
    this.birthDate = birthDate;
    this.roles = roles;
  }
  public String getAccessToken() {
    return token;
  }
  public void setAccessToken(String token) {
    this.token = token;
  }
  public String getTokenType() {
    return type;
  }
  public void setTokenType(String type) {
    this.type = type;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAddress() {
    return address;
  }
  public void setAddess(String address) {
    this.address = address;
  }
  public String getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }
  public List<String> getRoles() {
    return roles;
  }
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

}
