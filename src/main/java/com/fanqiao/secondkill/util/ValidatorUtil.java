package com.fanqiao.secondkill.util;

import lombok.extern.log4j.Log4j2;
import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class ValidatorUtil {

    public static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile) {

        log.info("mobile {}", mobile);
        if(StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        boolean boolValue = matcher.matches();
        log.info("matcher.matches() {}", boolValue);
        return matcher.matches();
    }

    public static void main(String[] args) {
        log.info("isMobile(\"13125063264\") {}", isMobile("13125063264"));
    }
}
