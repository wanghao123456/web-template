package com.wh.web.myweb.service.impl;

import com.wh.web.myweb.dao.mapper.UserRoleMapper;
import com.wh.web.myweb.dao.po.UserPO;
import com.wh.web.myweb.dao.po.UserRolePO;
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
import org.springframework.transaction.annotation.Transactional;


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
    public String login(UserBO userBO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userBO.getUsername(), userBO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userBO.getUsername());
        String token = tokenUtil.generateToken(userDetails);
        return token;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean register(UserBO userBO) {
        if (userRoleMapper.loadUserByUserName(userBO.getUsername()) == null) {
            UserPO userPO = new UserPO();
            userPO.setUserName(userBO.getUsername());
            userPO.setPassWord(new BCryptPasswordEncoder().encode(userBO.getPassword()));
            if (userRoleMapper.addUser(userPO) == 1) {
                UserRolePO userRolePO = new UserRolePO();
                userRolePO.setUserId(userPO.getId());
                userRolePO.setRoleId(3);
                return userRoleMapper.addUserRole(userRolePO) == 1;
            }
        }
        return false;
    }
}
