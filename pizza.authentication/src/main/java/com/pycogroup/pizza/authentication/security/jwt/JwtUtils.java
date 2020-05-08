package com.pycogroup.pizza.authentication.security.jwt;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pycogroup.pizza.authentication.common.LogExecutionStatus;
import com.pycogroup.pizza.authentication.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * This class has 3 functions
 * - Generate a JWT from username, date, expiration, secret
 * - Get username from JWT
 * - Validate a JWT
 */
@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${com.pizza.security.jwtSecret}")
  private String jwtSecret;

  @Value("${com.pizza.security.jwtExpirationMs}")
  private int jwtExpirationMs;

  @LogExecutionStatus
  public String generateJwtToken(Authentication authentication) {
    
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
              .setSubject(userPrincipal.getUsername())
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
              .signWith(SignatureAlgorithm.HS512, jwtSecret)
              .compact();
  }

  @LogExecutionStatus
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  @LogExecutionStatus
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage()); 
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage()); 
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage()); 
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage()); 
    }  

    return false;
  }
}
