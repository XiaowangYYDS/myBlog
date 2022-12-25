package com.teng.service.impl;

import com.teng.domin.ResponseResult;
import com.teng.domin.entity.LoginUser;
import com.teng.domin.entity.User;
import com.teng.domin.vo.UserInfoVo;
import com.teng.service.AdminLoginService;
import com.teng.service.BlogLoginService;
import com.teng.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/19:23 Description:
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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
        redisCache.setCacheObject("blogLogin:" + userId,loginUser);
        //把token和userInfo封装  返回
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token",jwt);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        hashMap.put("userInfo",userInfoVo);
        return ResponseResult.okResult(hashMap);
    }

    @Override
    public ResponseResult logout() {
        //调用GetUerIdBySecurityContextHolder中的getUserId获取userId
        String userId = SecurityUtils.getUserId().toString();
        //删除redis中的用户信息
        redisCache.deleteObject("blogLogin:" + userId);
        return ResponseResult.okResult();
    }
}
