package com.teng.service.impl;

import com.teng.domin.ResponseResult;
import com.teng.domin.entity.LoginUser;
import com.teng.domin.entity.User;
import com.teng.domin.vo.UserInfoVo;
import com.teng.service.AdminLoginService;
import com.teng.service.BlogLoginService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.JwtUtil;
import com.teng.utils.RedisCache;
import com.teng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/19:23 Description:
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //生成token
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("adminLogin:" + userId,loginUser);
        //把token和userInfo封装  返回
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token",jwt);
        return ResponseResult.okResult(hashMap);
    }

    @Override
    public ResponseResult logout() {
        //调用GetUerIdBySecurityContextHolder中的getUserId获取userId
        String userId = SecurityUtils.getUserId().toString();
        //删除redis中的用户信息
        redisCache.deleteObject("adminLogin:" + userId);
        return ResponseResult.okResult();
    }
}
