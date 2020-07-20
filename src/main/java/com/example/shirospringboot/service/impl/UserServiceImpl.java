package com.example.shirospringboot.service.impl;

import com.example.shirospringboot.domain.User;
import com.example.shirospringboot.maper.UserMapper;
import com.example.shirospringboot.service.UserService;
import com.example.shirospringboot.utils.GetSaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User findUser(String username) {
        return userMapper.findUser(username);
    }

    @Override
    public void register(User user) {
        user.setRole("user:add");
        //生成随机盐
        String salt = GetSaltUtils.getSalt();
        //将随机盐包存在数据库
        user.setSalt(salt);
        //明文密码进行md5+salt+hash散列加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt,1024);

        user.setPassword(md5Hash.toHex());
        userMapper.saveUser(user);
    }
}
