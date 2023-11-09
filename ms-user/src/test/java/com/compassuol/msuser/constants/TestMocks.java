package com.compassuol.msuser.constants;

import com.compassuol.msuser.dto.*;
import com.compassuol.msuser.enumerate.RoleEnum;
import com.compassuol.msuser.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMocks {

    public static final Date parse;

    static {
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-12");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final RequestPayloadDTO VALID_REQUEST_PAYLOAD = RequestPayloadDTO.builder()
            .cpf("131.040.450-20")
            .active(true)
            .birthdate("2012-12-12")
            .email("rian@gmail.com")
            .firstName("rian")
            .lastName("bar")
            .password("1234556")
            .build();

    public static final ResponsePayloadDTO VALID_RESPONSE_PAYLOAD = ResponsePayloadDTO.builder()
            .active(true)
            .birthdate("2012-12-12")
            .cpf("131.040.450-20")
            .email("rian@gmail.com")
            .firstName("rian")
            .id(1)
            .lastName("bar")
            .password("1234556")
            .build();

    public static final UserModel VALID_USER_MODEL = UserModel.builder()
            .role(RoleEnum.USER)
            .active(true)
            .password("1234556")
            .birthdate(parse)
            .email("rian@gmail.com")
            .cpf("131.040.450-20")
            .lastName("bar")
            .firstName("rian")
            .id(1)
            .build();

    public static final LoginPayloadDTO VALID_LOGIN_PAYLOAD = LoginPayloadDTO.builder()
            .email("rian@email.com")
            .password("1234567")
            .build();

    public static final UpdatePayloadDTO VALID_UPDATE_PAYLOAD = UpdatePayloadDTO.builder()
            .birthdate("2012-12-12")
            .cpf("131.040.450-20")
            .email("rian@gmail.com")
            .firstName("rian")
            .lastName("bar")
            .build();

    public static final ChangePasswordDTO VALID_PASSWORD_PAYLOAD = ChangePasswordDTO.builder()
            .password("password")
            .build();
}
