package com.compassuol.msnotification.service;

import com.compassuol.msnotification.dto.SendMessagePayloadDTO;
import com.compassuol.msnotification.model.NotifyModel;
import com.compassuol.msnotification.repository.NotifyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class NotifyServiceTest {

    SendMessagePayloadDTO sendMessagePayloadDTO;
    NotifyModel notifyModel;
    List<NotifyModel> notifyModelList;


    @InjectMocks
    NotifyService notifyService;

    @Mock
    NotifyRepository notifyRepository;

    @Mock
    DataTransferDocumentObject dataTransfer;

    @BeforeEach
    void setUp() {
        sendMessagePayloadDTO = new SendMessagePayloadDTO("email@email.com", "CREATE", "12/12/2012");
        notifyModel = new NotifyModel(null, "email@email.com", "CREATE", "12/12/2012");
        notifyModelList = List.of(new NotifyModel(null, "email@email.com", "CREATE", "12/12/2012"),
                new NotifyModel(null, "email@email.com", "UPDATE", "12/12/2012"));
    }

    @Test
    void saveDocument_withValidDataReturnsVoid() {
        var payloadCapture = ArgumentCaptor.forClass(SendMessagePayloadDTO.class);
        when(dataTransfer.parseToSendPayload(anyString())).thenReturn(sendMessagePayloadDTO);
        when(dataTransfer.parseDtoToModel(payloadCapture.capture())).thenReturn(notifyModel);

        assertDoesNotThrow(() -> notifyService.saveDocumentService(anyString()));
    }

    @Test
    void getAllDocuments_withValidData_returns_listOfObjects() {
        when(notifyRepository.findAll()).thenReturn(notifyModelList);

        var response = notifyService.getAllDocumentsService();

        assertNotNull(response);
    }
}
