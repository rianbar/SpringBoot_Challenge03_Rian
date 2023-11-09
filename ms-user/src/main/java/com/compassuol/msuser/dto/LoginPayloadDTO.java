package com.compassuol.msuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginPayloadDTO {

    @NotBlank(message = "field 'email' cannot be empty")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email format is wrong")
    private String email;
    @NotBlank(message = "field 'password' cannot be empty")
    @Size(min = 6, message = "password must be bigger than five characters")
    private String password;
}
