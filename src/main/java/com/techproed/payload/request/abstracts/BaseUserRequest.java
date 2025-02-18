package com.techproed.payload.request.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserRequest extends AbstractUserRequest {

    @NotNull(message = "please enter your password")
    @Size(min = 8,max = 20,message = "your password shell be 8-20 characters")
    private String password;

    private Boolean buildIn = false;

}
