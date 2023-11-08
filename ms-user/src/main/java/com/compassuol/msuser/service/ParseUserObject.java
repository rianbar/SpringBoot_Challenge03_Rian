package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RequestPayloadDTO;
import com.compassuol.msuser.dto.ResponsePayloadDTO;
import com.compassuol.msuser.dto.UpdatePayloadDTO;
import com.compassuol.msuser.model.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ParseUserObject {

    public UserModel parseToModel(RequestPayloadDTO dto) {
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
            throw new RuntimeException(ex);
        }
    }

    public ResponsePayloadDTO ParseToDTO(UserModel model) {
        var datePattern = new SimpleDateFormat("dd/MM/yyyy");
        Date getDate = model.getBirthdate();
        String formatData = datePattern.format(getDate);

        return ResponsePayloadDTO.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .cpf(model.getCpf())
                .birthdate(formatData)
                .email(model.getEmail())
                .password(model.getPassword())
                .active(model.isActive())
                .build();
    }

    public UserModel UpdateUser(UserModel user, UpdatePayloadDTO dto) {
       try {
           Date parseToDate = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getBirthdate());
           user.setFirstName(dto.getFirstName());
           user.setLastName(dto.getLastName());
           user.setCpf(dto.getCpf());
           user.setBirthdate(parseToDate);
           user.setEmail(dto.getEmail());
           user.setActive(dto.isActive());
           return user;
       } catch (ParseException ex) {
           throw new RuntimeException(ex);
       }
    }
}
