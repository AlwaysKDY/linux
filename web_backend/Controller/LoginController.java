package com.example.web_backend.Controller;

import com.example.web_backend.entity.User;
import com.example.web_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController

public class LoginController {
    @Autowired
    private UserMapper usermapper;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User _user = usermapper.selectByUsername(username);
        if (_user == null) return "用户名称不存在，请先注册";
        System.out.println(_user.getPassword().equals(password) ? "登陆成功" : "用户名密码不匹配，请重新登陆");
        return _user.getPassword().equals(password) ? "登陆成功" : "用户名密码不匹配，请重新登陆";
    }

    @PostMapping("/home/changePassword")
    public String changePassword(@RequestParam String username, @RequestParam String former_password,
                                 @RequestParam String new_password) {
        User _user = usermapper.selectByUsername(username);
        if (_user == null) return "用户名称不存在，请先注册";
        if (_user.getPassword().equals(former_password)) {
            usermapper.updatePassword(username, new_password);
            return "修改成功";
        } else {
            return "原密码错误，请重新输入";
        }
    }

    @PostMapping("/home/changeUsername")
    public String changeInformation(@RequestParam String former_username,@RequestParam String new_username,
                                    @RequestParam String password){
        User user=usermapper.selectByUsername(former_username);
        if(user==null)return "用户名不存在";
        if(!Objects.equals(password, user.getPassword()))return "密码错误，请重新输入";
        usermapper.updateUsername(new_username,user.getId());
        return "更改成功";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam String username, @RequestParam String password) {
        if (usermapper.selectByUsername(username) != null) return "该用户名已存在";
        else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            usermapper.insert(user);
            return "注册成功，您的id是" + user.getId() + "，用户名是" + user.getUsername();
        }
    }

    @PostMapping("/home/deleteUser")
    public void deleteUser(@RequestParam String username){
        usermapper.deleteById(usermapper.selectByUsername(username).getId());
    }
}
