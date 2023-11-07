package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.RegisterRequestDTO;
import com.compassuol.msuser.dto.RegisterResponseDTO;
import com.compassuol.msuser.exception.ExceptionType.BusinessViolationException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ParseObject parseObject;

    public RegisterResponseDTO registerUserService(RegisterRequestDTO dto) {
        if (checkIfFieldsAlreadyExists(dto)) {
            throw new BusinessViolationException("email or cpf already exists");}

        UserModel model = userRepository.save(parseObject.parseToModel(dto));
        return parseObject.ParseToDTO(model);
    }

    private boolean checkIfFieldsAlreadyExists(RegisterRequestDTO dto) {
        Optional<UserModel> checkCpf = userRepository.findByCpf(dto.getCpf());
        Optional<UserModel> checkEmail = userRepository.findByEmail(dto.getEmail());

        return checkCpf.isEmpty() || checkEmail.isEmpty();
    }
}
