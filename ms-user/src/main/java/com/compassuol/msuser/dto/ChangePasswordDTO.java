package com.compassuol.msuser.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDTO {
    @NotBlank(message = "field 'password' cannot be empty")
    @Size(min = 6, message = "password must be bigger than five characters")
    private String password;
}
