package com.teng.service.impl;

import com.teng.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/9:38 Description:
 */
@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否具有该权限
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermissions(String permission){
        //如果是超级管理员直接返回
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则获取当前登录用户所具有的权限列表
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        //判断是否存在该权限
        return permissions.contains(permission);
    }
}
