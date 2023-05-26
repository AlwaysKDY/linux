package com.example.web_backend.Controller;

import com.example.web_backend.entity.User;
import com.example.web_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/home/account")
    public double getBalance(@RequestParam String username) {
        return userMapper.selectByUsername(username).getBalance();
    }

    @PostMapping("/home/account/charge")
    public String chargeBalance(@RequestParam String username, @RequestParam double charge){
        User user = userMapper.selectByUsername(username);
        double newBalance = user.getBalance()+charge;
        userMapper.updateBalance(user.getUsername(),newBalance);
        return "充值成功,您当前的余额为："+newBalance;
    }
}
