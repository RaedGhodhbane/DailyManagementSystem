package com.daily.payloads.response;

import lombok.Setter;

import java.util.List;

/**
 * @author Majd Selmi
 */
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String id;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, String id, String email, String username, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.email = email;
    this.username = username;
    this.roles = roles;
  }
}
