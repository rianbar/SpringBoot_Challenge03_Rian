package com.compassuol.msuser.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayloadResponseDTO {

    private String firstName;
    private String lastName;
    private String cpf;
    private String birthdate;
    private String email;
    private String password;
    private boolean active;
}
