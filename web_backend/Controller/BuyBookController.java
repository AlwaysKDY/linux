package com.example.web_backend.Controller;

import com.example.web_backend.entity.Book;
import com.example.web_backend.entity.BookPurchase;
import com.example.web_backend.entity.User;
import com.example.web_backend.entity.Vip;
import com.example.web_backend.mapper.BookMapper;
import com.example.web_backend.mapper.BookPurchaseMapper;
import com.example.web_backend.mapper.UserMapper;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
public class BuyBookController {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookPurchaseMapper bookPurchaseMapper;

    @PostMapping("/buyBook")
    public String buyBook(@RequestParam String username, @RequestParam int bookId, @RequestParam int nums,
                          @RequestParam int ebook_flag) {
        Book book = bookMapper.selectById(bookId);
        User user = userMapper.selectByUsername(username);
        if (book == null) return "图书不存在";
        if (ebook_flag == 0) {
            if (book.getStorage() < nums) return "图书库存不足，购买失败";
        }
        if (user == null) return "用户不存在";

        String return_mes = "";
        String vip_class = user.getVip_class();

        Pair<Double, String> discount = Vip.getVipDiscount(vip_class);
        if (!Objects.equals(vip_class, "0")) return_mes += ("您是" + discount.second + "会员，为您折扣" + discount.first);
        double totalPrice=(ebook_flag==0?book.getPrice():book.getE_price())*nums*discount.first;

        if (user.getBalance() < totalPrice) return "账户余额不足，请先充值";
        return_mes += ("总花费" + totalPrice);

        userMapper.updateBalance(user.getUsername(), user.getBalance() - totalPrice);
        if(ebook_flag==0)
            bookMapper.updateStorage(book.getStorage() - nums, bookId);

        BookPurchase bookPurchase = new BookPurchase();
        bookPurchase.setBookId(book.getId());
        bookPurchase.setBuyTime((new Date()).toString());
        bookPurchase.setBuyNums(nums);
        bookPurchase.setPurchaseNumber(new Random().nextInt());
        bookPurchase.setDiscount(discount.first);
        bookPurchase.setTotalPrice(totalPrice);
        bookPurchase.setEbook_flag(ebook_flag);
        bookPurchaseMapper.insert(bookPurchase);

        return return_mes + "/n购买成功";
    }
}
