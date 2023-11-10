package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RequestPayloadDTO;
import com.compassuol.msuser.exception.type.UserNotFoundException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CheckBusinessRules {

    public static final String NOT_FOUND_MESSAGE = "user not found";

    private final UserRepository userRepository;
    public boolean checkIfCredentialsAlreadyExists(RequestPayloadDTO dto) {
        Optional<UserModel> checkCpf = userRepository.findByCpf(dto.getCpf());
        Optional<UserModel> checkEmail = userRepository.findByEmail(dto.getEmail());
        return checkCpf.isPresent() || checkEmail.isPresent();
    }

    public boolean checkPasswordMatchers(String password, String encodedPassword) {
        var bcrypt = new BCryptPasswordEncoder();
        return bcrypt.matches(password, encodedPassword);
    }

    public UserModel checkIfUserExists(int id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException(NOT_FOUND_MESSAGE);
        return user.get();
    }
}
