package com.wh.web.myweb.service.impl;

import com.wh.web.myweb.dao.mapper.UserRoleMapper;
import com.wh.web.myweb.dao.po.UserPO;
import com.wh.web.myweb.model.bo.UserBO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserPO userPO = userRoleMapper.loadUserByUserName(userName);
        if (userPO == null) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(userPO, userBO);
        userBO.setRole(userRoleMapper.getUserRoleByUserName(userName));
        return userBO;
    }
}
