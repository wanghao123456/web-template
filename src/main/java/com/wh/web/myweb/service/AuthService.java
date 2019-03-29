package com.wh.web.myweb.service;

import com.wh.web.myweb.model.bo.UserBO;

public interface AuthService {

    boolean register(UserBO userBO);

    String login(UserBO userBO);
}
