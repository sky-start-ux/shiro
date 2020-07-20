package com.example.shirospringboot;

import com.example.shirospringboot.domain.User;
import com.example.shirospringboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        User sky = userService.findUser("sky");
        System.out.println(sky);
    }

}
