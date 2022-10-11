package com.lj.crm.settings.service;

import com.lj.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserByLoginAndPwd(Map<String,Object> map );

    List<User> queryAllUsers();
}
