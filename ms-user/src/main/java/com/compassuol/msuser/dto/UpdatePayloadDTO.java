package com.compassuol.msuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UpdatePayloadDTO {
    @NotBlank(message = "field 'firstName' cannot be empty")
    @Size(min = 3)
    private String firstName;
    @NotBlank(message = "field 'lastName' cannot be empty")
    @Size(min = 3)
    private String lastName;
    @NotBlank(message = "field 'cpf' cannot be empty")
    @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}", message = "cpf format is wrong")
    private String cpf;
    @NotBlank(message = "field 'birthdate' cannot be empty")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "birthdate format is wrong")
    private String birthdate;
    @NotBlank(message = "field 'email' cannot be empty")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email format is wrong")
    private String email;
    private boolean active;
}
