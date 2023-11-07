package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.LoginPayloadDTO;
import com.compassuol.msuser.dto.RegisterRequestDTO;
import com.compassuol.msuser.dto.RegisterResponseDTO;
import com.compassuol.msuser.exception.ExceptionType.BusinessViolationException;
import com.compassuol.msuser.exception.ExceptionType.UserNotFoundException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import com.compassuol.msuser.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenService tokenService;
    private final ParseObject parseObject;

    public RegisterResponseDTO registerUserService(RegisterRequestDTO dto) {
        if (checkIfFieldsAlreadyExists(dto)) {
            throw new BusinessViolationException("email or cpf already exists");}

        UserModel model = userRepository.save(parseObject.parseToModel(dto));
        return parseObject.ParseToDTO(model);
    }

    public String loginUserService(LoginPayloadDTO dto) {
        Optional<UserModel> user = userRepository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            return tokenService.CreateToken(user.get());
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    private boolean checkIfFieldsAlreadyExists(RegisterRequestDTO dto) {
        Optional<UserModel> checkCpf = userRepository.findByCpf(dto.getCpf());
        Optional<UserModel> checkEmail = userRepository.findByEmail(dto.getEmail());

        return checkCpf.isPresent() || checkEmail.isPresent();
    }
}
