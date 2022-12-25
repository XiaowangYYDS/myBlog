package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysql.cj.log.Log;
import com.teng.constants.SystemConstants;
import com.teng.domin.entity.LoginUser;
import com.teng.domin.entity.User;
import com.teng.mapper.MenuMapper;
import com.teng.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/19:34 Description:
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);

        //判断用户是否存在， 如果不存在抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        //查询权限信息封装
        //如果是后台用户才需要查询权限封装
        if (user.getType().equals(SystemConstants.ADMIN)){
            List<String> permissions = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,permissions);
        }

        return new LoginUser(user,null);
    }
}
