package com.wh.web.myweb.service.impl;

import com.wh.web.myweb.dao.mapper.UserRoleMapper;
import com.wh.web.myweb.dao.po.UserPO;
import com.wh.web.myweb.model.bo.UserBO;
import com.wh.web.myweb.service.AuthService;
import com.wh.web.myweb.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String login(String userName, String passWord) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, passWord);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        String token = tokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public boolean register(UserBO userBO) {
        if (userRoleMapper.loadUserByUserName(userBO.getUsername()) != null) {
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userBO.setPassWord(encoder.encode(userBO.getPassword()));
        UserPO userPO = new UserPO();
        userPO.setUserName(userBO.getUsername());
        userPO.setPassWord(userBO.getPassword());
        return userRoleMapper.addUser(userPO) == 1;
    }
}
