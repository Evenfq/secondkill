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
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@Log4j2
public class LoginService {

    public static final String COOKIE_NAME = "cookie_name";

    @Autowired
    private SecondkillUserDao secondkillUserDao;
    @Autowired
    private RedisService redisService;

    public String doLogin(HttpServletResponse response, LoginVo loginVo) {

        if(loginVo != null) {
            log.info("loginVo {}", loginVo.toString());
        }
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
            addCookie(response, rst, token);
            return token;
        } else {
            throw new GlobalException(CodeMessage.LOGIN_ERROR);
        }
    }

    public SecondkillUser getByToken(HttpServletResponse response, String token) {

        if(StringUtils.isEmpty(token)) {
            return null;
        }
        SecondkillUser secondkillUser = redisService.get(UserKey.getByToken, token, SecondkillUser.class);
        //延长缓存期
        if(secondkillUser != null) {
            addCookie(response, secondkillUser, token);
        }
        return secondkillUser;
    }

    private void addCookie(HttpServletResponse response, SecondkillUser secondkillUser, String token) {

        redisService.set(UserKey.getByToken, token, secondkillUser);
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        log.info("addCookie: token {}", token);
        cookie.setMaxAge(UserKey.getByToken.getExpiredSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public SecondkillUser getById(long id) {

        SecondkillUser secondkillUser = redisService.get(UserKey.getById, "" + id, SecondkillUser.class);
        if(secondkillUser != null) {
            return secondkillUser;
        }

        SecondkillUser searchParam = new SecondkillUser();
        searchParam.setId(id);
        secondkillUser = secondkillUserDao.selectSecondkillUser(searchParam);
        if(secondkillUser != null) {
            redisService.set(UserKey.getById, "" + id, secondkillUser);
        }
        return secondkillUser;
    }

    public Boolean updatePasswordById(String token, long id, String passwordNew) {

        //去user
        SecondkillUser secondkillUser = getById(id);
        if(secondkillUser == null) {
            throw new GlobalException(CodeMessage.SECOND_KILL_USER_NOT_EXIST);
        }

        //更新数据库
        SecondkillUser toBeUpdate = new SecondkillUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.inputPasswordToDBPassword(passwordNew, secondkillUser.getSalt()));
        secondkillUserDao.updateSecondkillUserSelective(toBeUpdate);

        //处理缓存
        redisService.del(UserKey.getById, "" + id);
        secondkillUser.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.getByToken, token, secondkillUser);
        return true;
    }
}