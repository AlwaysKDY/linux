package com.example.web_backend.Controller;

import com.example.web_backend.entity.*;
import com.example.web_backend.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class GoodController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private DessertMapper dessertMapper;
    @Autowired
    private BookPurchaseMapper bookPurchaseMapper;
    @Autowired
    private DessertPurchaseMapper dessertPurchaseMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/admin")
    private String adminLogin(@RequestParam String name, @RequestParam String password) {
        Map<String, Object> _admin = new HashMap<String, Object>() {{
            put("name", name);
        }};
        List<Admin> admins = adminMapper.selectByMap(_admin);
        if (admins == null) return "用户不存在";
        for (Admin admin : admins) if (!Objects.equals(admin.getPassword(), password)) return "密码错误";
        return "登陆成功";
    }

    @GetMapping("/admin/getAllBook")
    public List<Book> getAllBook() {
        List<Book> books = bookMapper.selectAll();
        for (Book book : books) {
            String imagePath = book.getImage_path();
            try {
                File imageFile = new File(imagePath);
                byte[] imageData = Files.readAllBytes(imageFile.toPath());
                book.setImageResource(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    @GetMapping("/admin/getBook")
    public List<Book> getBook(@RequestParam("name") String name) {
        List<Book> books = bookMapper.selectByName(name);
        for (Book book : books) {
            String imagePath = book.getImage_path();
            try {
                File imageFile = new File(imagePath);
                byte[] imageData = Files.readAllBytes(imageFile.toPath());
                book.setImageResource(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    @PostMapping("admin/addNewBook")
    public void addNewBook(@RequestBody Book book, @RequestParam("file") MultipartFile file) {
        if (bookMapper.selectByName(book.getName()) != null) return;
        if (file.isEmpty()) return;
        String filePath = "D:/desktop/FILE/网页设计/images/bookImages"+file.getOriginalFilename();
        book.setImage_path(filePath);
        try {
            File destFile = new File(filePath);
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookMapper.insert(book);
    }

    @PostMapping("admin/addBook")
    public void addBook(@RequestParam int id, @RequestParam int nums) {
        Book book = bookMapper.selectById(id);
        if (book == null) return;
        bookMapper.updateStorage(book.getStorage() + nums, id);
    }

    @PostMapping("/admin/deleteBook")
    public String deleteBook(@RequestParam int id) {
        Book book = bookMapper.selectById(id);
        if (book == null) return "您找的书本不存在";
        bookMapper.deleteById(id);
        return "下架成功";
    }

    @GetMapping("/admin/getAllDessert")
    public List<Dessert> getAllDessert() {
        List<Dessert> desserts = dessertMapper.selectAll();
        for (Dessert dessert:desserts) {
            String imagePath = dessert.getImage_path();
            try {
                File imageFile = new File(imagePath);
                byte[] imageData = Files.readAllBytes(imageFile.toPath());
                dessert.setImageResource(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return desserts;
    }

    @GetMapping("/admin/getDessert")
    public List<Dessert> getDessert(@RequestParam String name) {
        List<Dessert> desserts = dessertMapper.selectByName(name);
        for (Dessert dessert :desserts) {
            String imagePath = dessert.getImage_path();
            try {
                File imageFile = new File(imagePath);
                byte[] imageData = Files.readAllBytes(imageFile.toPath());
                dessert.setImageResource(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return desserts;
    }

    @PostMapping("/admin/addNewDessert")
    public void addNewDessert(@RequestBody Dessert dessert, @RequestParam("file") MultipartFile file) {
        if (dessertMapper.selectByName(dessert.getName()) != null) return;
        if (file.isEmpty()) return;
        String filePath = "D:/desktop/FILE/网页设计/images/dessertImages" + file.getOriginalFilename();
        dessert.setImage_path(filePath);
        try {
            File destFile = new File(filePath);
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dessertMapper.insert(dessert);
    }

    @PostMapping("/admin/addDessert")
    public void addDessert(@RequestParam int id, @RequestParam int nums) {
        Dessert dessert = dessertMapper.selectById(id);
        if (dessert == null) return;
        dessertMapper.updateStorage(dessert.getStorage() + nums, id);
    }

    @PostMapping("/admin/deleteDessert")
    public String deleteDessert(@RequestParam int id) {
        Dessert dessert = dessertMapper.selectById(id);
        if (dessert == null) return "甜品不存在";
        dessertMapper.deleteById(dessert.getId());
        return "下架成功";
    }

    @GetMapping("/admin/getPurchaseRecord")
    public List<BookPurchase> getBookPurchases(@RequestParam int id, @RequestParam int uid_flag) {
        Map<String, Object> condition = new HashMap<>();
        if (uid_flag == 1) {
            condition.put("uid", id);
        } else {
            condition.put("bookId", id);
        }
        return bookPurchaseMapper.selectByMap(condition);
    }


    @GetMapping("/admin/getPurchaseRecordByDate")
    public List<BookPurchase> getBookPurchasesByDate(@RequestParam String date) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("buyTime", date);

        return bookPurchaseMapper.selectByMap(condition);
    }

    @GetMapping("/admin/getDessertRecord")
    public List<DessertPurchase> getDessertPurchases(@RequestParam int id, @RequestParam int uid_flag) {
        Map<String, Object> condition = new HashMap<>();
        if (uid_flag == 1) {
            condition.put("uid", id);
        } else {
            condition.put("dessertId", id);
        }
        return dessertPurchaseMapper.selectByMap(condition);
    }


    @GetMapping("/admin/getDessertRecordByDate")
    public List<DessertPurchase> getDessertPurchasesByDate(@RequestParam String date) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("buyTime", date);

        return dessertPurchaseMapper.selectByMap(condition);
    }

    @GetMapping("/admin/getUser")
    public User getUser(@RequestParam String name){
        return userMapper.selectByUsername(name);
    }
}
