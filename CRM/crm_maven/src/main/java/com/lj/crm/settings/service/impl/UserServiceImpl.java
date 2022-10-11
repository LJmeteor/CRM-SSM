package com.lj.crm.settings.service.impl;

import com.lj.crm.settings.domain.User;
import com.lj.crm.settings.mapper.UserMapper;
import com.lj.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    public User queryUserByLoginAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByloginActAndPwd(map);
    }

    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }
}
