package com.wh.web.myweb.controller;

import com.wh.web.myweb.model.bo.UserBO;
import com.wh.web.myweb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public String login(@RequestBody UserBO userBO) throws AuthenticationException {
        return authService.login(userBO);
    }

    @PostMapping("register")
    public Object register(@RequestBody UserBO userBO) throws AuthenticationException {
        return authService.register(userBO);
    }
}
