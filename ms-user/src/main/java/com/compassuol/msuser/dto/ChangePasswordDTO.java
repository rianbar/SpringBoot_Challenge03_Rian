package com.compassuol.msuser.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "field 'password' cannot be empty")
    @Size(min = 6, message = "password must be bigger than five characters")
    private String password;
}
