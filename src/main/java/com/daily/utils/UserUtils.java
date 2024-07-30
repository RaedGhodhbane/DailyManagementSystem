package com.daily.utils;

import com.daily.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Majd Selmi
 */
@Component
public class UserUtils {
    @Autowired
    UserRepository userRepository;

    public boolean isUsernameValid(String username) {
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        if (regex.matcher(username).find()) {
            throw new IllegalStateException("Username should not contain any special characters");
        }
        if (username.contains(" ")) {
            throw new IllegalStateException("Username should not contain any whitespaces");
        }
        if (username.length() < 6) {
            throw new IllegalStateException("Username should contain between 6 and 24 characters");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username already taken");
        }
        return true;
    }

    public boolean isEmailValid(String email) {
        if (email.contains(" ")) {
            throw new IllegalStateException("E-mail should not contain any whitespaces");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("E-mail already taken");
        }
        return true;
    }

    public boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            throw new IllegalStateException("Password should contain minimum 6 characters");
        }
        if (password.contains(" ")) {
            throw new IllegalStateException("Password should not contain any whitespaces");
        }
        return true;
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        String regex = "^[0-9+]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new IllegalStateException("Phone number is not valid");
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalStateException("Phone number already exists");
        }
        return true;
    }
}
