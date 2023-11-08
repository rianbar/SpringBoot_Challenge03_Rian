package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RequestPayloadDTO;
import com.compassuol.msuser.dto.ResponsePayloadDTO;
import com.compassuol.msuser.dto.UpdatePayloadDTO;
import com.compassuol.msuser.enumerate.RoleEnum;
import com.compassuol.msuser.model.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataTransferUserObject {

    public UserModel parseToModel(RequestPayloadDTO dto) {
        String encrypted = new BCryptPasswordEncoder().encode(dto.getPassword());
        return UserModel.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .birthdate(formatDate(dto.getBirthdate()))
                .password(encrypted)
                .role(RoleEnum.USER)
                .active(dto.isActive())
                .build();
    }

    public ResponsePayloadDTO ParseToDTO(UserModel model) {
        return ResponsePayloadDTO.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .cpf(model.getCpf())
                .birthdate(ParseFormatDate(model.getBirthdate()))
                .email(model.getEmail())
                .password(model.getPassword())
                .active(model.isActive())
                .build();
    }

    public UserModel SetUpdatedUserFields(UserModel user, UpdatePayloadDTO dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setCpf(dto.getCpf());
        user.setBirthdate(formatDate(dto.getBirthdate()));
        user.setEmail(dto.getEmail());
        return user;
    }

    private Date formatDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String ParseFormatDate(Date date) {
        var datePattern = new SimpleDateFormat("dd/MM/yyyy");
        return datePattern.format(date);
    }
}
