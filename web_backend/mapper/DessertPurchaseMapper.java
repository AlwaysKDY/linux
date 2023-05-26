package com.example.web_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web_backend.entity.DessertPurchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface DessertPurchaseMapper extends BaseMapper<DessertPurchase> {

    @Select("SELECT * FROM dessertPurchase WHERE uid = #{uid}")
    public List<DessertPurchase> selectByUid(@Param("uid") int uid);
}
