package com.example.web_backend.Controller;

import com.example.web_backend.entity.BookPurchase;
import com.example.web_backend.entity.DessertPurchase;
import com.example.web_backend.entity.User;
import com.example.web_backend.mapper.BookPurchaseMapper;
import com.example.web_backend.mapper.DessertPurchaseMapper;
import com.example.web_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookPurchaseMapper bookPurchaseMapper;
    @Autowired
    private DessertPurchaseMapper dessertPurchaseMapper;

    @GetMapping("home/bookPurchaseRecord")
    public List<BookPurchase> getBookPurchaseRecord(@RequestParam String username){
        User user = userMapper.selectByUsername(username);
        return bookPurchaseMapper.selectByUid(user.getId());
    }

    @GetMapping("home/dessertPurchaseRecord")
    public List<DessertPurchase> getDessertPurchaseRecord(@RequestParam String username){
        User user = userMapper.selectByUsername(username);
        return dessertPurchaseMapper.selectByUid(user.getId());
    }
}
