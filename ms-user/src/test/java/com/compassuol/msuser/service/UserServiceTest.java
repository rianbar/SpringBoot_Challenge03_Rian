package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RequestPayloadDTO;
import com.compassuol.msuser.dto.ResponsePayloadDTO;
import com.compassuol.msuser.exception.type.BusinessViolationException;
import com.compassuol.msuser.exception.type.UserNotFoundException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import com.compassuol.msuser.security.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.compassuol.msuser.constants.TestMocks.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    DataTransferUserObject parseObjet;

    @Mock
    CheckBusinessRules checkRules;

    @Mock
    JwtTokenService tokenService;

    @Mock
    UserRepository repository;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Test
    void registerUser_withValidData_returnsObject() {
        var userModelCaptor = ArgumentCaptor.forClass(UserModel.class);
        var requestPayloadCaptor = ArgumentCaptor.forClass(RequestPayloadDTO.class);
        when(checkRules.checkIfCredentialsAlreadyExists(requestPayloadCaptor.capture())).thenReturn(false);
        when(parseObjet.parseToDTO(userModelCaptor.capture())).thenReturn(VALID_RESPONSE_PAYLOAD);
        when(repository.save(userModelCaptor.capture())).thenReturn(VALID_USER_MODEL);

        ResponsePayloadDTO response = userService.registerUserService(VALID_REQUEST_PAYLOAD);

        assertNotNull(response);
    }

    @Test
    void registerUser_withInvalidData_returnsException() {
        var requestPayloadCaptor = ArgumentCaptor.forClass(RequestPayloadDTO.class);
        when(checkRules.checkIfCredentialsAlreadyExists(requestPayloadCaptor.capture())).thenReturn(true);

        assertThatThrownBy(() -> userService.registerUserService(VALID_REQUEST_PAYLOAD))
                .isInstanceOf(BusinessViolationException.class);
    }

    @Test
    void loginUser_withValidData_returnsString() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(VALID_USER_MODEL));
        when(checkRules.checkPasswordMatchers(anyString(), anyString())).thenReturn(true);
        when(tokenService.createToken(any())).thenReturn(anyString());

        String response = userService.loginUserService(VALID_LOGIN_PAYLOAD);

        assertNotNull(response);
    }

    @Test
    void loginUser_withInvalidData_returnsException() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(checkRules.checkPasswordMatchers(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.loginUserService(VALID_LOGIN_PAYLOAD))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getUserById_witValidData_returnsObject() {
        var userModelCaptor = ArgumentCaptor.forClass(UserModel.class);
        when(checkRules.checkIfUserExists(anyInt())).thenReturn(VALID_USER_MODEL);
        when(parseObjet.parseToDTO(userModelCaptor.capture())).thenReturn(VALID_RESPONSE_PAYLOAD);

        ResponsePayloadDTO response = userService.getUserByIdService(1);

        assertNotNull(response);
    }

    @Test
    void getUserId_withInvalidData_returnsException() {
        when(checkRules.checkIfUserExists(anyInt())).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> userService.getUserByIdService(1))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateUserById_withValidData_ReturnsObject() {
        var userModelCaptor = ArgumentCaptor.forClass(UserModel.class);
        when(checkRules.checkIfUserExists(anyInt())).thenReturn(VALID_USER_MODEL);
        when(repository.save(userModelCaptor.capture())).thenReturn(VALID_USER_MODEL);
        when(parseObjet.parseToDTO(userModelCaptor.capture())).thenReturn(VALID_RESPONSE_PAYLOAD);

        ResponsePayloadDTO response = userService.updateUserByIdService(1, VALID_UPDATE_PAYLOAD);

        assertNotNull(response);
    }

    @Test
    void updateUserById_withInvalidSata_ReturnsException() {
        when(checkRules.checkIfUserExists(anyInt())).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> userService.updateUserByIdService(1, VALID_UPDATE_PAYLOAD))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updatePassword_withValidData_ReturnsObject() {
        var userModelCaptor = ArgumentCaptor.forClass(UserModel.class);
        when(checkRules.checkIfUserExists(anyInt())).thenReturn(VALID_USER_MODEL);
        when(repository.save(userModelCaptor.capture())).thenReturn(VALID_USER_MODEL);
        when(parseObjet.parseToDTO(userModelCaptor.capture())).thenReturn(VALID_RESPONSE_PAYLOAD);

        ResponsePayloadDTO response = userService.updateUserPasswordService(1, VALID_PASSWORD_PAYLOAD);

        assertNotNull(response);
    }

    @Test
    void updatePassword_withInvalidData_ReturnsObject() {
        when(checkRules.checkIfUserExists(anyInt())).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> userService.updateUserPasswordService(1, VALID_PASSWORD_PAYLOAD))
                .isInstanceOf(UserNotFoundException.class);
    }

}
