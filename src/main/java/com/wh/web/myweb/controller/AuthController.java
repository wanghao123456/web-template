package com.wh.web.myweb.controller;

import com.wh.web.myweb.model.bo.UserBO;
import com.wh.web.myweb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public String createToken(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) throws AuthenticationException {
        return authService.login(userName, passWord);
    }

    @PostMapping("register")
    public Object register(@RequestBody UserBO userBO) throws AuthenticationException {
        return authService.register(userBO);
    }
}
