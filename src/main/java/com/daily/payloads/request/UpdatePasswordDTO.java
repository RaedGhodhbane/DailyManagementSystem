package com.daily.payloads.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @author Majd Selmi
 */
@Getter
public class UpdatePasswordDTO {
    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}
