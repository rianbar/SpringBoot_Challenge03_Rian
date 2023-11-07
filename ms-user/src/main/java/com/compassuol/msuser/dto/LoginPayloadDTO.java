package com.compassuol.msuser.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginPayloadDTO {

    @NotEmpty(message = "field 'email' cannot be empty")
    @Pattern(regexp = "/^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?$/i")
    private String email;
    @NotEmpty(message = "field 'password' cannot be empty")
    private String password;
}
