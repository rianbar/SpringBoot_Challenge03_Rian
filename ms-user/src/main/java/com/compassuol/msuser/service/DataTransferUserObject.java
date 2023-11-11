package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RequestPayloadDTO;
import com.compassuol.msuser.dto.ResponsePayloadDTO;
import com.compassuol.msuser.dto.SendMessagePayloadDTO;
import com.compassuol.msuser.dto.UpdatePayloadDTO;
import com.compassuol.msuser.enumerate.EventEnum;
import com.compassuol.msuser.enumerate.RoleEnum;
import com.compassuol.msuser.exception.type.ParseObjectException;
import com.compassuol.msuser.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
                .birthdate(parseToDate(dto.getBirthdate()))
                .password(encrypted)
                .role(RoleEnum.USER)
                .active(dto.isActive())
                .build();
    }

    public ResponsePayloadDTO parseToDTO(UserModel model) {
        return ResponsePayloadDTO.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .cpf(model.getCpf())
                .birthdate(convertAndFormatDate(model.getBirthdate()))
                .email(model.getEmail())
                .password(model.getPassword())
                .active(model.isActive())
                .build();
    }

    public UserModel setUpdatedUserFields(UserModel user, UpdatePayloadDTO dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setCpf(dto.getCpf());
        user.setBirthdate(parseToDate(dto.getBirthdate()));
        user.setEmail(dto.getEmail());
        return user;
    }

    public SendMessagePayloadDTO setPayloadMessage(String email, EventEnum eventType) {
        return SendMessagePayloadDTO.builder()
                .event(eventType.toString())
                .date(buildCurrentlyDate())
                .email(email)
                .build();
    }

    public String parseObjectToJson(SendMessagePayloadDTO dto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new ParseObjectException("could,t parse object to string");
        }
    }

    private Date parseToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException ex) {
            throw new ParseObjectException("could,t parse string to date");
        }
    }

    private String convertAndFormatDate(Date date) {
        var datePattern = new SimpleDateFormat("dd/MM/yyyy");
        return datePattern.format(date);
    }

    private String buildCurrentlyDate() {
        var date = parseToDate(LocalDateTime.now().toString());
        return convertAndFormatDate(date);
    }
}
