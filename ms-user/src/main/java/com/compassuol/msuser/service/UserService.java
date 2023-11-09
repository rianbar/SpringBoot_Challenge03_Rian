package com.compassuol.msuser.service;

import com.compassuol.msuser.configuration.RabbitMqConfig;
import com.compassuol.msuser.dto.*;
import com.compassuol.msuser.enumerate.EventEnum;
import com.compassuol.msuser.exception.type.BusinessViolationException;
import com.compassuol.msuser.exception.type.UserNotFoundException;
import com.compassuol.msuser.model.UserModel;
import com.compassuol.msuser.repository.UserRepository;
import com.compassuol.msuser.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenService tokenService;
    private final DataTransferUserObject parseObject;
    private final CheckBusinessRules checkRules;
    private final RabbitTemplate rabbitTemplate;

    public ResponsePayloadDTO registerUserService(RequestPayloadDTO dto) {
        if (checkRules.checkIfCredentialsAlreadyExists(dto)) throw new BusinessViolationException("email or cpf already exists");

        UserModel model = userRepository.save(parseObject.parseToModel(dto));
        sendMessage(model.getEmail(), EventEnum.CREATE);
        return parseObject.parseToDTO(model);
    }

    public String loginUserService(LoginPayloadDTO dto) {
        Optional<UserModel> user = userRepository.findByEmail(dto.getEmail());

        if (user.isPresent() && checkRules.checkPasswordMatchers(dto.getPassword(), user.get().getPassword())) {
            sendMessage(user.get().getEmail(), EventEnum.LOGIN);
            return tokenService.createToken(user.get());
        } else {
            throw new UserNotFoundException("email or password is wrong");
        }
    }

    public ResponsePayloadDTO getUserByIdService(int id) {
        var user = checkRules.checkIfUserExists(id);
        return parseObject.parseToDTO(user);
    }

    public ResponsePayloadDTO updateUserByIdService(int id, UpdatePayloadDTO dto) {
        var user = checkRules.checkIfUserExists(id);
        var saveUpdatedUser = userRepository.save(parseObject.setUpdatedUserFields(user, dto));
        sendMessage(saveUpdatedUser.getEmail(), EventEnum.UPDATE);
        return parseObject.parseToDTO(saveUpdatedUser);
    }

    public ResponsePayloadDTO updateUserPasswordService(int id, ChangePasswordDTO dto) {
        var user = checkRules.checkIfUserExists(id);
        user.setPassword(encryptPassword(dto.getPassword()));
        var saveUser = userRepository.save(user);
        sendMessage(saveUser.getEmail(), EventEnum.UPDATE_PASSWORD);
        return parseObject.parseToDTO(saveUser);
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private void sendMessage(String email, EventEnum eventType) {
        var objectMessage = parseObject.setPayloadMessage(email, eventType);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "", objectMessage);
    }
}
