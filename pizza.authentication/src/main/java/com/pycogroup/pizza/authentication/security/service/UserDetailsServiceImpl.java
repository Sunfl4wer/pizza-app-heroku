package com.pycogroup.pizza.authentication.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pycogroup.pizza.authentication.common.LogExecutionStatus;
import com.pycogroup.pizza.authentication.repository.UserRepository;
import com.pycogroup.pizza.model.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  @LogExecutionStatus
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(phoneNumber)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + phoneNumber));
              
    return UserDetailsImpl.build(user);
  }
}
