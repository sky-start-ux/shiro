package com.example.shirospringboot.controller;

import com.example.shirospringboot.domain.User;
import com.example.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("msg","hello shiro!");
        return "index";
    }
    @GetMapping("/user/update")
    public String update(){
        return "user/update";
    }
    @GetMapping("/user/add")
    public String add(){
        return "user/add";
    }
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @GetMapping("/login")
    public String login(String username,String password,Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            //执行登录方法
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){//用户名不存在
           model.addAttribute("msg","用户名不存在");
           return "login";
        }catch (IncorrectCredentialsException e){//密码不存在
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
    @GetMapping("/noauth")
    @ResponseBody
    public String noauth(){
        return "没有访问权限";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @GetMapping("/register")
    public String register(User user){
        userService.register(user);
        return "redirect:/toLogin";
    }
}
