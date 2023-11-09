package com.compassuol.msuser.service;

import com.compassuol.msuser.exception.type.UserNotFoundException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.compassuol.msuser.constants.TestMocks.VALID_REQUEST_PAYLOAD;
import static com.compassuol.msuser.constants.TestMocks.VALID_USER_MODEL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CheckBusinessRolesTest {

    @InjectMocks
    CheckBusinessRules checkBusinessRules;

    @Mock
    private UserRepository userRepository;

    @Test
    void checkCredentials_withInvalidData_returnsTrue() {
        when(userRepository.findByCpf(anyString())).thenReturn(Optional.of(VALID_USER_MODEL));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(VALID_USER_MODEL));

        boolean response = checkBusinessRules.checkIfCredentialsAlreadyExists(VALID_REQUEST_PAYLOAD);

        assertTrue(response);
    }

    @Test
    void checkCredentials_withValidData_returnsFalse() {
        when(userRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        boolean response = checkBusinessRules.checkIfCredentialsAlreadyExists(VALID_REQUEST_PAYLOAD);

        assertFalse(response);
    }

    @Test
    void checkPasswordMatcher_withValidData_ReturnsTrue() {
        var encrypted = "$2a$10$6gnmkOkdNy6hjDQuVg7c8Oh.PKhzGVmne0ZRyoDgAWpsvr2RMxkqK";
        var bcrypt = mock(BCryptPasswordEncoder.class);
        when(bcrypt.matches(anyString(), anyString())).thenReturn(true);

        boolean response = checkBusinessRules.checkPasswordMatchers("123456", encrypted);

        assertTrue(response);
    }

    @Test
    void checkPasswordMatcher_withInvalidData_ReturnsFalse() {
        var bcrypt = mock(BCryptPasswordEncoder.class);
        when(bcrypt.matches(anyString(), anyString())).thenReturn(false);

        boolean response = checkBusinessRules.checkPasswordMatchers("123456", "encrypted");

        assertFalse(response);
    }

    @Test
    void checkIfUserExists_withValidData_returnsObject() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(VALID_USER_MODEL));

        UserModel response = checkBusinessRules.checkIfUserExists(1);

        assertNotNull(response);
    }

    @Test
    void checkIfUserExists_withInvalidData_returnsException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> checkBusinessRules.checkIfUserExists(1))
                .isInstanceOf(UserNotFoundException.class);

    }
}
