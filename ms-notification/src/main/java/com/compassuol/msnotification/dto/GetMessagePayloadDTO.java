package com.compassuol.msnotification.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMessagePayloadDTO {

    private String email;
    private String event;
    private String date;
}
