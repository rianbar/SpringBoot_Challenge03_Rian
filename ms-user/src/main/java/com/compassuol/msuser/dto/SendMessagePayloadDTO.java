package com.compassuol.msuser.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SendMessagePayloadDTO  implements Serializable {
    private String email;
    private String event;
    private String date;
}
