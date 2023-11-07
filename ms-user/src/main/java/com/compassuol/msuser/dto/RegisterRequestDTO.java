package com.compassuol.msuser.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotEmpty(message = "field 'firstName' cannot be empty")
    @Size(min = 3)
    private String firstName;
    @NotEmpty(message = "field 'lastName' cannot be empty")
    @Size(min = 3)
    private String lastName;
    @NotEmpty(message = "field 'cpf' cannot be empty")
    @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}")
    private String cpf;
    @NotEmpty(message = "field 'birthdate' cannot be empty")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
    private String birthdate;
    @NotEmpty(message = "field 'email' cannot be empty")
    @Pattern(regexp = "/^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?$/i")
    private String email;
    @NotEmpty(message = "field 'password' cannot be empty")
    @Size(min = 6, message = "password must be bigger than five characters")
    private String password;
    @NotEmpty(message = "field 'active' cannot be empty")
    private boolean active;
}
