package com.compassuol.msuser.dto;

import com.compassuol.msuser.enumerate.EventEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SendMessagePayloadDTO  implements Serializable {
    private String email;
    @Enumerated(EnumType.STRING)
    private EventEnum event;
    private String date;
}
