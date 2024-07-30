package com.daily.controllers;

import com.daily.payloads.request.UpdatePasswordDTO;
import com.daily.payloads.request.UpdateUserDTO;
import com.daily.services.UserService;
import com.daily.payloads.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Majd Selmi
 */
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    UserService userService;

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"", "/"})
    public ResponseEntity<Response> getAll() {
        return userService.getAll();
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "{userId}")
    public ResponseEntity<Response> getById(@PathVariable String userId) {
        return userService.getById(userId);
    }

    //@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "{userId}/change-password")
    public ResponseEntity<Response> updatePassword(@PathVariable String userId, @RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        return userService.updatePassword(userId, updatePasswordDTO);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "{userId}/admin-change-password")
    public ResponseEntity<Response> updatePasswordByAdmin(@PathVariable String userId, @RequestParam(name = "newPassword") String newPassword) {
        return userService.updatePasswordByAdmin(userId, newPassword);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Response> deleteById(@PathVariable String userId) {
        return userService.deleteById(userId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping(value = "{userId}")
    public ResponseEntity<Response> update(@PathVariable String userId, @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.update(userId, updateUserDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "{userId}/roles")
    public ResponseEntity<Response> assignRole(@PathVariable String userId, @RequestParam(name = "role") String role) {
        return userService.assignRole(userId, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "{userId}/roles")
    public ResponseEntity<Response> revokeRole(@PathVariable String userId, @RequestParam(name = "role") String role) {
        return userService.revokeRole(userId, role);
    }
}
