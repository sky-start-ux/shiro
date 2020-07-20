package com.example.shirospringboot.service;

import com.example.shirospringboot.domain.User;

public interface UserService {
    User findUser(String username);

    void register(User user);
}
