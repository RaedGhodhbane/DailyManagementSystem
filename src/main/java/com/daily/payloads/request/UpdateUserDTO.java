package com.daily.payloads.request;

import lombok.Getter;

/**
 * @author Majd Selmi
 */
@Getter
public class UpdateUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
}
