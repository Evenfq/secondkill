package com.fanqiao.secondkill.service;

import com.fanqiao.secondkill.dao.DemoDao;
import com.fanqiao.secondkill.dao.SecondkillUserDao;
import com.fanqiao.secondkill.entity.Demo;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.result.CodeMessage;
import com.fanqiao.secondkill.util.MD5Util;
import com.fanqiao.secondkill.vo.LoginVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoginService {

    @Autowired
    private SecondkillUserDao secondkillUserDao;

    public CodeMessage doLogin(LoginVo loginVo) {

        SecondkillUser secondkillUser = new SecondkillUser();
        secondkillUser.setMobile(loginVo.getMobile());
        SecondkillUser rst = secondkillUserDao.selectSecondkillUser(secondkillUser);
        if(rst == null) {
            return CodeMessage.LOGIN_ERROR;
        }
        String passwordDB = MD5Util.formPasswordToDatabasePassword(loginVo.getPassword(), rst.getSalt());
        log.info("passwordDB {}", passwordDB);
        if(passwordDB != null && passwordDB.equals(rst.getPassword())) {
            return CodeMessage.SUCCESS;
        } else {
            return CodeMessage.LOGIN_ERROR;
        }
    }

}