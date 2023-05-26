package com.example.web_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web_backend.entity.BookPurchase;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookPurchaseMapper extends BaseMapper<BookPurchase> {

    @Select("SELECT * FROM bookpurchase WHERE uid = #{uid})")
    public List<BookPurchase> selectByUid(@Param("uid") int uid);

}

