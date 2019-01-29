package com.fanqiao.secondkill.dao;

import com.fanqiao.secondkill.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DemoDao {

    //@Select("select * from demo where id = #{id}")
    Demo selectDemo(Long id);
}
