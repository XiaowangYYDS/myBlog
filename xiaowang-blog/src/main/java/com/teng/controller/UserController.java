package com.teng.controller;

import com.teng.annotation.SystemLog;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.RegisterDto;
import com.teng.domin.dto.UserInfoDto;
import com.teng.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/12:19 Description:
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户",description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userinfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserInfoDto userInfoDto){
        return userService.updateUserInfo(userInfoDto);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }
}
