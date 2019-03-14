package com.fanqiao.secondkill.dao;

import com.fanqiao.secondkill.entity.SecondkillUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondkillUserDao {

    SecondkillUser selectSecondkillUser(SecondkillUser secondkillUser);

    void updateSecondkillUserSelective(SecondkillUser secondkillUser);
}