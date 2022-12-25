package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.entity.User;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import com.teng.service.BlogLoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/19:18 Description:
 */
@RestController
@Api(tags = "登录注销",description = "登陆注销相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示必须要传用户名
           throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
