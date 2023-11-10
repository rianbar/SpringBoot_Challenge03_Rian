package com.compassuol.msnotification.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("user-notifications")
public class NotifyModel {

    @Id
    private String id;
    private String email;
    private String event;
    private String date;
}
