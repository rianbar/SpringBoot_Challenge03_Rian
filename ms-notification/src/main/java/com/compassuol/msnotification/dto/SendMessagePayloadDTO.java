package com.compassuol.msnotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessagePayloadDTO implements Serializable {
    private String email;
    private String event;
    private String date;
}
