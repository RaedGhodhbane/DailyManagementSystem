package com.daily.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Majd Selmi
 */
@Document(collection = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String encode) {
    }
}
