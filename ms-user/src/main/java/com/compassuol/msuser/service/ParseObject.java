package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RegisterRequestDTO;
import com.compassuol.msuser.dto.RegisterResponseDTO;
import com.compassuol.msuser.model.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ParseObject {

    public UserModel parseToModel(RegisterRequestDTO dto) {
        try {
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getBirthdate());
            String encrypted = new BCryptPasswordEncoder().encode(dto.getPassword());
            return UserModel.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .cpf(dto.getCpf())
                    .email(dto.getEmail())
                    .birthdate(parseDate)
                    .password(encrypted)
                    .active(dto.isActive())
                    .build();
        } catch (ParseException ex) {
            throw new RuntimeException();
        }
    }

    public RegisterResponseDTO ParseToDTO(UserModel model) {
        var datePattern = new SimpleDateFormat("dd/MM/yyyy");
        Date getDate = model.getBirthdate();
        String formatData = datePattern.format(getDate);

        return RegisterResponseDTO.builder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .cpf(model.getCpf())
                .birthdate(formatData)
                .email(model.getEmail())
                .password(model.getPassword())
                .active(model.isActive())
                .build();
    }
}
