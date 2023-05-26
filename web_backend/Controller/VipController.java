package com.example.web_backend.Controller;

import com.example.web_backend.entity.User;
import com.example.web_backend.entity.Vip;
import com.example.web_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VipController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/home/upgradeVip")
    public String vipUpdate(@RequestParam String username, @RequestParam String vip_class) {
        User user = userMapper.selectByUsername(username);
        if (Integer.parseInt(user.getVip_class()) >= Integer.parseInt(vip_class))
            return "无法逆向升级";
        userMapper.updateVip_class(username, vip_class);
        return "恭喜您升级为" + Vip.getVipDiscount(vip_class).second + "会员";
    }

    @GetMapping("/home/vipclass")
    public String vipClass(@RequestParam String username){
        return "您是"+userMapper.selectByUsername(username).getVip_class()+"会员";
    }
}
