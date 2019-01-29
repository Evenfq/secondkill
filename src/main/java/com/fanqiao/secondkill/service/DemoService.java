package com.fanqiao.secondkill.service;

import com.fanqiao.secondkill.dao.DemoDao;
import com.fanqiao.secondkill.entity.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public Demo selectDemo(Long id) {
        return demoDao.selectDemo(id);
    }

}