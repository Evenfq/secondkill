package com.fanqiao.secondkill.util;


import org.springframework.util.DigestUtils;

public class MD5Util {

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    private static final String salt = "a4fda5ga5";

    public static String inputPasswordToFormPassword(String password) {
        return md5("" + salt.charAt(0) + password + salt.charAt(3));
    }

    public static String formPasswordToDatabasePassword(String password, String salt) {
        return md5("" + salt.charAt(0) + password + salt.charAt(3));
    }

    public static String inputPasswordToDBPassword(String password, String salt) {
        return formPasswordToDatabasePassword(inputPasswordToFormPassword(password), salt);
    }

    public static void main(String[] args) { //e70d524818a9eb2454b4bf68925e7fc2
        System.out.println(inputPasswordToFormPassword("123456"));
        System.out.println(formPasswordToDatabasePassword(inputPasswordToFormPassword("123456"), "54fafdas6"));
    }
}
