package com.example.web_backend.Controller;

import com.example.web_backend.entity.Dessert;
import com.example.web_backend.entity.DessertPurchase;
import com.example.web_backend.entity.User;
import com.example.web_backend.entity.Vip;
import com.example.web_backend.mapper.DessertMapper;
import com.example.web_backend.mapper.DessertPurchaseMapper;
import com.example.web_backend.mapper.UserMapper;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
public class BuyDessertController {
    @Autowired
    private DessertMapper dessertMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DessertPurchaseMapper dessertPurchaseMapper;

    @PostMapping("/buyDessert")
    public String buyDessert(@RequestParam String username, @RequestParam int dessertId, @RequestParam int nums) {
        Dessert dessert = dessertMapper.selectById(dessertId);
        User user = userMapper.selectByUsername(username);
        if (dessert == null) return "甜品不存在";
        if (dessert.getStorage() < nums) return "甜品库存不足，购买失败";
        if (user == null) return "用户不存在";

        String return_mes = "";
        String vip_class = user.getVip_class();

        Pair<Double, String> discount = Vip.getVipDiscount(vip_class);
        if (!Objects.equals(vip_class, "0")) return_mes += ("您是" + discount.second + "会员，为您折扣" + discount.first);
        double totalPrice = dessert.getPrice() * discount.first * nums;

        if (user.getBalance() < totalPrice) return "账户余额不足，请先充值";
        return_mes += ("总花费" + totalPrice);

        userMapper.updateBalance(user.getUsername(), user.getBalance() - totalPrice);
        dessertMapper.updateStorage(dessert.getStorage() - nums, dessertId);

        DessertPurchase dessertPurchase = new DessertPurchase();
        dessertPurchase.setBuyTime((new Date()).toString());
        dessertPurchase.setPurchaseNumber(new Random().nextInt());
        dessertPurchase.setDiscount(discount.first);
        dessertPurchase.setTotalPrice(totalPrice);
        dessertPurchase.setDessertId(dessert.getId());
        dessertPurchase.setUid(user.getId());
        dessertPurchaseMapper.insert(dessertPurchase);

        return return_mes + "/n购买成功";
    }
}
