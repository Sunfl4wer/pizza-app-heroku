package com.pycogroup.pizza.authentication.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pycogroup.pizza.authentication.security.service.UserDetailsServiceImpl;


public class AuthTokenFilter extends OncePerRequestFilter { 

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    try {
      String jwt = parseJwt(request); //get jwt from the header 
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // if the request has JWT 
        String username = jwtUtils.getUserNameFromJwtToken(jwt); // parse username from it

        UserDetails userDetails = userDetailsService.loadUserByUsername(username); //from username get UserDetails
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities()); // create an Authentication object
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        
        SecurityContextHolder.getContext().setAuthentication(authentication); // set the current UserDetails in SecurityContext
                                                                              // using setAuthentication(authentication) method
        /*
         * UserDetails can be retrieved from SecurityContext like this:
         * 
         * UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         * 
         * userDetails.getUsername();
         * userDetails.getPasword();
         * userDetails.getAuthorities();
         */
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {)",e);
    }

    filterChain.doFilter(request, response);
  }

  // Get JWT from the Authorization header by removing Bearer prefix
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
      return headerAuth.substring(7, headerAuth.length());
    }
    
    return null;
  }

}
