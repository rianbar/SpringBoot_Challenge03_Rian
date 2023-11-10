package com.compassuol.msnotification.service;

import com.compassuol.msnotification.model.NotifyModel;
import com.compassuol.msnotification.repository.NotifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyRepository notifyRepository;
    private final DataTransferDocumentObject dataTransfer;

    public void saveDocumentService(String obj) {
        var dto = dataTransfer.parseToSendPayload(obj);
        var model = dataTransfer.parseDtoToModel(dto);
        notifyRepository.save(model);
    }

    public List<NotifyModel> getAllDocumentsService() {
        return notifyRepository.findAll();
    }
}
