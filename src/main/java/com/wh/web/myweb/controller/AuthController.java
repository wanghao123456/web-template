package com.wh.web.myweb.controller;

import com.wh.web.myweb.constants.ResultConstant;
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
    public Object login(@RequestBody UserBO userBO) throws AuthenticationException {
        String token = authService.login(userBO);
        return ResultConstant.newSuccess(token);
    }

    @PostMapping("register")
    public Object register(@RequestBody UserBO userBO) throws AuthenticationException {
        return authService.register(userBO) ? ResultConstant.SUCCESS : ResultConstant.FAIL;
    }
}
