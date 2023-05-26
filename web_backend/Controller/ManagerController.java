package com.example.web_backend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.web_backend.entity.Admin;
import com.example.web_backend.entity.Manager;
import com.example.web_backend.mapper.AdminMapper;
import com.example.web_backend.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ManagerController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @GetMapping("/manager/login")
    public String managerLogin(@RequestParam String name,@RequestParam String password){
        Map<String,Object> condition = new HashMap<>();
        condition.put("name",name);
        List<Manager> managers = managerMapper.selectByMap(condition);
        if(managers==null)return "名称不存在";
        return Objects.equals(managers.get(0).getPassword(), password) ?"登陆成功":"密码错误";
    }
    @PostMapping("/manager/adminRegister")
    public String adminRegister(@RequestBody Admin admin){
        Map<String,Object> condition = new HashMap<>();
        condition.put("name",admin.getName());
        if(adminMapper.selectByMap(condition)!=null)return "员工已存在";
        adminMapper.insert(admin);
        return "员工添加成功";
    }
    @PostMapping("/manager/adminClock")
    public String adminClock(@RequestParam String adminName){
        LocalDate currentDate = LocalDate.now();
        Map<String,Object>condition = new HashMap<>();
        condition.put("name",adminName);
        Admin admin = adminMapper.selectByMap(condition).get(0);
        admin.setLastClock(String.valueOf(currentDate));
        adminMapper.updateById(admin);
        return "打卡成功";
    }
    @GetMapping("/manager/getAbsent")
    public List<Admin> getAbsent() {
        String today = String.valueOf(LocalDate.now());
        return adminMapper.selectList(new QueryWrapper<Admin>()
                .ne("lastClock", today));
    }
    @PostMapping("/manager/setJob")
    public String setJob(@RequestParam String adminName,@RequestParam String job){
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("name", adminName));
        if (admin != null) {
            admin.setJob(job);
            adminMapper.updateById(admin);
            return "修改成功";
        } else {
            return "未找到该员工";
        }
    }
    @PostMapping("/manager/deleteAdmin")
    public String deleteAdmin(@RequestParam String adminName){
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("name", adminName));
        if (admin != null) {
            adminMapper.deleteById(admin.getId());
            return "删除成功";
        } else {
            return "未找到该员工";
        }
    }
}
