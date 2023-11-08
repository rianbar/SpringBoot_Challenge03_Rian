package com.compassuol.msuser.controller;

import com.compassuol.msuser.dto.LoginPayloadDTO;
import com.compassuol.msuser.dto.RequestPayloadDTO;
import com.compassuol.msuser.dto.UpdatePayloadDTO;
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
    public ResponseEntity<Object> userRegister(@RequestBody @Valid RequestPayloadDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUserService(dto));
    };

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody @Valid LoginPayloadDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUserService(dto));
    };

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByIdService(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable(name = "id") int id,
                                                 @RequestBody @Valid UpdatePayloadDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserByIdService(id, dto));
    }

    public void updateUserPassword() {};
}
