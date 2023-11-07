package com.compassuol.msuser.controller;

import com.compassuol.msuser.dto.LoginPayloadDTO;
import com.compassuol.msuser.dto.RegisterRequestDTO;
import com.compassuol.msuser.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Object> userRegister(@RequestBody @Valid RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUserService(dto));
    };

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody @Valid LoginPayloadDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUserService(dto));
    };

    @GetMapping("/users/{id}")
    public void getUserById() {}

    public void updateUserById() {}

    public void updateUserPassword() {};
}
