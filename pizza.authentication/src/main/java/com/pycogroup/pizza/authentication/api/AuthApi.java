package com.pycogroup.pizza.authentication.api;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pycogroup.pizza.authentication.common.ErrorResponseBody;
import com.pycogroup.pizza.authentication.common.GenericResponseError;
import com.pycogroup.pizza.authentication.common.LinkEntity;
import com.pycogroup.pizza.authentication.common.Links;
import com.pycogroup.pizza.authentication.common.Message;
import com.pycogroup.pizza.authentication.common.Reason;
import com.pycogroup.pizza.authentication.common.ResponseDto;
import com.pycogroup.pizza.authentication.payload.request.LoginRequest;
import com.pycogroup.pizza.authentication.payload.request.SignupRequest;
import com.pycogroup.pizza.authentication.payload.response.JwtResponse;
import com.pycogroup.pizza.authentication.repository.RoleRepository;
import com.pycogroup.pizza.authentication.repository.UserRepository;
import com.pycogroup.pizza.authentication.security.jwt.JwtUtils;
import com.pycogroup.pizza.authentication.security.service.UserDetailsImpl;
import com.pycogroup.pizza.model.ERole;
import com.pycogroup.pizza.model.Role;
import com.pycogroup.pizza.model.User;


@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/pizza/auth")
public class AuthApi {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<Object> authenticationUser(@Valid @RequestBody LoginRequest loginRequest,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/pizza/auth/sigin");
    headers.setLocation(locationUri);
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
                          .map(item -> item.getAuthority())
                          .collect(Collectors.toList());
    Links links = new Links();
    URI  signInUri = URI.create(baseUri + "/pizza/auth/signin");
    URI  signUpUri = URI.create(baseUri + "/pizza/auth/signup");
    links.add(LinkEntity.builder().href(signInUri).relation("Self").method(RequestMethod.POST).build());
    links.add(LinkEntity.builder().href(signUpUri).relation("Sign Up").method(RequestMethod.POST).build());
    return new ResponseEntity<Object>(ResponseDto.builder().code(HttpStatus.OK.value())
                                        .data(new JwtResponse(jwt,
                                             userDetails.getId(),
                                             userDetails.getFirstName(),
                                             userDetails.getLastName(),
                                             userDetails.getUsername(),
                                             userDetails.getEmail(),
                                             userDetails.getAddress(),
                                             userDetails.getBirthDate(),
                                             roles)).links(links.getLinks()).build(),
                                      headers,
                                      HttpStatus.OK);
  }
  
  @RequestMapping(
      value = "/signup", 
      method = RequestMethod.POST)
  public ResponseEntity<Object> registerUser(
      @Valid @RequestBody SignupRequest signUpRequest,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/pizza/auth/signup");
    headers.setLocation(locationUri);
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("username", signUpRequest.getUsername());
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("username","Already existed!");
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_BODY.getMessage())
                                              .reason(Reason.BAD_PARAMS.getReason())
                                              .where(where)
                                              .when(when).build());
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }
      
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("email", signUpRequest.getEmail());
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("email","Already existed!");
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_BODY.getMessage())
                                              .reason(Reason.BAD_PARAMS.getReason())
                                              .where(where)
                                              .when(when).build());
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }

    // Create new user's account
    User user = User.builder().firstName(signUpRequest.getFirstName())
                              .lastName(signUpRequest.getLastName())
                              .username(signUpRequest.getUsername())
                              .email(signUpRequest.getEmail())
                              .password(encoder.encode(signUpRequest.getPassword()))
                              .address(signUpRequest.getAddress())
                              .birthDate(signUpRequest.getBirthDate())
                              .build();
      
    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    userRepository.save(user);

    // Create response
    Links links = new Links();
    URI  signInUri = URI.create(baseUri + "/pizza/auth/signin");
    URI  signUpUri = URI.create(baseUri + "/pizza/auth/signup");
    links.add(LinkEntity.builder().href(signUpUri).relation("Self").method(RequestMethod.POST).build());
    links.add(LinkEntity.builder().href(signInUri).relation("Sign In").method(RequestMethod.POST).build());
    return new ResponseEntity<Object>(ResponseDto.builder().code(HttpStatus.CREATED.value())
                                        .data("User Registered Successfully!").links(links.getLinks()).build(),
                                      headers,
                                      HttpStatus.CREATED);
  }
}
