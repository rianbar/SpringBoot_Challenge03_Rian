package com.compassuol.msnotification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("user-notifications")
@NoArgsConstructor
@AllArgsConstructor
public class NotifyModel {

    @Id
    private String id;
    private String email;
    private String event;
    private String date;
}
