package com.teng.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/17:07 Description:
 */
public class CheckUtil {


    public static boolean emailCheck(String mail) {
        //邮箱校验规则
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译邮箱正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(mail);
        //进行正则匹配
        boolean matchesEmail = m.matches();
        return matchesEmail;
    }

    public static boolean phoneCheck(String phone){
        //电话号校验规则
        String regPattern = "^1[3-9]\\d{9}$";
        //编译电话号正则表达式
        Pattern p1 = Pattern.compile(regPattern);
        Matcher matcher = p1.matcher(phone);
        boolean matchesPhone = matcher.matches();
        return matchesPhone;
    }
}
