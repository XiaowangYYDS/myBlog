package com.teng.service;

import com.teng.domin.ResponseResult;
import com.teng.domin.entity.User;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/19:22 Description:
 */
public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
