package com.wh.web.myweb.dao.mapper;

import com.wh.web.myweb.dao.po.UserPO;
import com.wh.web.myweb.dao.po.UserRolePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {

    boolean login(@Param("userName") String userName, @Param("passWord") String passWord);

    UserPO loadUserByUserName(@Param("userName") String userName);

    List<String> getUserRoleByUserName(@Param("userName") String userName);

    int addUser(@Param("userPO") UserPO userPO);

    int addUserRole(@Param("userRolePO") UserRolePO userRolePO);
}
