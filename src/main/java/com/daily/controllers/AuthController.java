package com.daily.controllers;

import com.daily.services.AuthService;
import com.daily.payloads.request.LoginRequest;
import com.daily.payloads.request.SignupRequest;
import com.daily.payloads.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Majd Selmi
 */
@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Response> register(@RequestBody @Valid SignupRequest signUpRequest) {
        return authService.register(signUpRequest);
    }
}