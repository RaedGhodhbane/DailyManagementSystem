package com.daily.security.jwt;

import com.daily.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for handling JWT operations.
 *
 * @author Majd Selmi
 */
@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${application.security.jwtSecret}")
  private String jwtSecret;

  @Value("${application.security.jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * Generates a JWT token for the authenticated user.
   *
   * @param userPrincipal the user details object containing user information
   * @return the generated JWT token
   */
  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return generateToken(userPrincipal.getUsername());
  }

  /**
   * Generates a JWT token based on the username.
   *
   * @param username the username for which the token is generated
   * @return the generated JWT token
   */
  public String generateJwtTokenFromUsername(String username) {
    return generateToken(username);
  }

  private String generateToken(String subject) {
    return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  /**
   * Retrieves the username from the JWT token.
   *
   * @param token the JWT token
   * @return the username extracted from the token
   */
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  /**
   * Validates the JWT token.
   *
   * @param authToken the JWT token to validate
   * @return true if the token is valid, false otherwise
   */
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
