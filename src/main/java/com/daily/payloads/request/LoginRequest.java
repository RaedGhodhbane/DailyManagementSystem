package com.daily.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Majd Selmi
 */
@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "Username cannot be NULL/empty")
  	private String username;

	@NotBlank(message = "Password cannot be NULL/empty")
	private String password;
}
