package com.compassuol.msuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class UserController {

    public void userLogin() {};

    public void userRegister() {};

    public void getUserById() {}

    public void updateUserById() {}

    public void updateUserPassword() {};
}
