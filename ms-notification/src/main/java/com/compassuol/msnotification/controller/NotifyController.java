package com.compassuol.msnotification.controller;

import com.compassuol.msnotification.configuration.RabbitMQConfig;
import com.compassuol.msnotification.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class NotifyController {

    private final NotifyService notifyService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFY_NAME)
    public void notifyListener(String obj) {
        notifyService.saveDocumentService(obj);
    }

    @GetMapping("/notify")
    public ResponseEntity<Object> getAllDocuments() {
        return ResponseEntity.status(HttpStatus.OK).body(notifyService.getAllDocumentsService());
    }
}
