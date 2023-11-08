package com.compassuol.msuser.service;

import com.compassuol.msuser.dto.*;
import com.compassuol.msuser.exception.ExceptionType.BusinessViolationException;
import com.compassuol.msuser.exception.ExceptionType.UserNotFoundException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import com.compassuol.msuser.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenService tokenService;
    private final ParseUserObject parseObject;

    public ResponsePayloadDTO registerUserService(RequestPayloadDTO dto) {
        if (checkIfFieldsAlreadyExists(dto)) {
            throw new BusinessViolationException("email or cpf already exists");}

        UserModel model = userRepository.save(parseObject.parseToModel(dto));
        return parseObject.ParseToDTO(model);
    }

    public String loginUserService(LoginPayloadDTO dto) {
        Optional<UserModel> user = userRepository.findByEmail(dto.getEmail());

        if (user.isPresent() && checkPasswordMatchers(dto.getPassword(), user.get().getPassword())) {
            return tokenService.CreateToken(user.get());
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    public ResponsePayloadDTO getUserByIdService(int id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("user not found");
        return parseObject.ParseToDTO(user.get());
    }

    public ResponsePayloadDTO updateUserByIdService(int id, UpdatePayloadDTO dto) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("user not found");
        var saveUpdatedUser = userRepository.save(parseObject.UpdateUser(user.get(), dto));
        return parseObject.ParseToDTO(saveUpdatedUser);
    }

    public ResponsePayloadDTO updateUserPasswordService(int id, ChangePasswordDTO dto) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("user not found");
        var encrypted = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.get().setPassword(encrypted);
        var saveUser = userRepository.save(user.get());
        return parseObject.ParseToDTO(saveUser);
    }

    private boolean checkIfFieldsAlreadyExists(RequestPayloadDTO dto) {
        Optional<UserModel> checkCpf = userRepository.findByCpf(dto.getCpf());
        Optional<UserModel> checkEmail = userRepository.findByEmail(dto.getEmail());
        return checkCpf.isPresent() || checkEmail.isPresent();
    }

    private boolean checkPasswordMatchers(String password, String encodedPassword) {
        var bcrypt = new BCryptPasswordEncoder();
        return bcrypt.matches(password, encodedPassword);
    }
}
