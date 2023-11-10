package com.compassuol.msnotification.service;

import com.compassuol.msnotification.dto.SendMessagePayloadDTO;
import com.compassuol.msnotification.exception.type.TransferObjectException;
import com.compassuol.msnotification.model.NotifyModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataTransferDocumentObject {

    private final ModelMapper modelMapper;

    public SendMessagePayloadDTO parseToSendPayload(String obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(obj, SendMessagePayloadDTO.class);
        } catch (JsonProcessingException e) {
            throw new TransferObjectException("could,t parse object to dto");
        }
    }

    public NotifyModel parseDtoToModel(SendMessagePayloadDTO dto) {
        return modelMapper.map(dto, NotifyModel.class);
    }
}
