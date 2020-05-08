package com.pycogroup.pizza.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pycogroup.pizza.authentication.security.jwt.AuthEntryPointJwt;
import com.pycogroup.pizza.authentication.security.jwt.AuthTokenFilter;
import com.pycogroup.pizza.authentication.security.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity // allows Spring to find and automatically apply the class to the
                   // global Web Secutiry
@EnableGlobalMethodSecurity( // provide AOP security on methods
    prePostEnabled=true)    // @PreAuthorize and @PostAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable() // disable Cross-Origin Resource Sharing and Cross-Site Resource Forgery
    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // AuthEntryPointJwt is chosen as ExceptionHandler
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    .authorizeRequests()
    .antMatchers("/pizza/**").permitAll()
    .antMatchers("/pizza/products/**").permitAll()
    .antMatchers("/pizza/products/cards/**").permitAll()
    .antMatchers("/pizza/auth/**").permitAll()
    .antMatchers("/pizza/auth/signin/**").permitAll()
    .antMatchers("/pizza/auth/signup/**").permitAll();
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  /* Spring Security will load User details to perform authentication and authorization
   * So it has UserDetailsService interface that we need to implement.
   * The implementation of UserDetailsService will be used for configuring DaoAuthenticationProvider
   * by AuthenticationManagerBuilder.userDetailsService() method.
   * We also need a PasswordEncoder for the DaoAuthenticationProvider. If we don't specify, it will
   * use plain text.
   */
}
