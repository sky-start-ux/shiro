package com.example.shirospringboot.maper;

import com.example.shirospringboot.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findUser(String username);

    @Insert("insert into user(username,password,role,salt) values(#{username},#{password},#{role},#{salt})")
    void saveUser(User user);
}
