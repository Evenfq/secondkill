package com.fanqiao.secondkill.config;

import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        log.info("supportsParameter {}", methodParameter.toString());
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == SecondkillUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        log.info("request {}", request.toString());
        log.info("response {}", response.toString());

        String paramName = request.getParameter(LoginService.COOKIE_NAME);
        String cookieName = getCookieValue(request, LoginService.COOKIE_NAME);
        if(StringUtils.isEmpty(cookieName) && StringUtils.isEmpty(paramName)) {
    		return null;
		}
		log.info("cookieName {}", cookieName);
		log.info("paramName {}", paramName);
		String token = StringUtils.isEmpty(cookieName)?paramName:cookieName;
		SecondkillUser secondkillUser = loginService.getByToken(response, token);
        return secondkillUser;
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
