package com.fanqiao.secondkill.service;

import com.fanqiao.secondkill.dao.DemoDao;
import com.fanqiao.secondkill.dao.SecondkillUserDao;
import com.fanqiao.secondkill.entity.Demo;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.exception.GlobalException;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.redis.UserKey;
import com.fanqiao.secondkill.result.CodeMessage;
import com.fanqiao.secondkill.util.MD5Util;
import com.fanqiao.secondkill.vo.LoginVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@Log4j2
public class LoginService {

    private static final String COOKIE_NAME = "cookie_name";

    @Autowired
    private SecondkillUserDao secondkillUserDao;
    @Autowired
    private RedisService redisService;

    public boolean doLogin(HttpServletResponse response, LoginVo loginVo) {

        SecondkillUser secondkillUser = new SecondkillUser();
        secondkillUser.setMobile(loginVo.getMobile());
        SecondkillUser rst = secondkillUserDao.selectSecondkillUser(secondkillUser);
        if(rst == null) {
            throw new GlobalException(CodeMessage.LOGIN_ERROR);
        }
        String passwordDB = MD5Util.formPasswordToDatabasePassword(loginVo.getPassword(), rst.getSalt());
        log.info("passwordDB {}", passwordDB);
        if(passwordDB != null && passwordDB.equals(rst.getPassword())) {
            String token = UUID.randomUUID().toString();
            redisService.set(UserKey.getByToken, token, rst);
            Cookie cookie = new Cookie(COOKIE_NAME, token);
            cookie.setMaxAge(UserKey.getByToken.getExpiredSeconds());
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        } else {
            throw new GlobalException(CodeMessage.LOGIN_ERROR);
        }
    }

}