package com.techproed.payload.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "Please provide old passwor")
    private String oldPassword;

    @NotBlank(message = "Please provide new password")
    private String newPassword;

}
