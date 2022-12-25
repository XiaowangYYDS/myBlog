package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.dto.LoginDto;
import com.teng.domin.entity.LoginUser;
import com.teng.domin.entity.Menu;
import com.teng.domin.entity.User;
import com.teng.domin.vo.AdminUserInfoVo;
import com.teng.domin.vo.MenuVo;
import com.teng.domin.vo.RouterVo;
import com.teng.domin.vo.UserInfoVo;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import com.teng.service.AdminLoginService;
import com.teng.service.MenuService;
import com.teng.service.RoleService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/09/29/20:18 Description:
 */
@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        if (!StringUtils.hasText(loginDto.getUserName())) {
            //提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User user = BeanCopyUtil.copyBean(loginDto, User.class);
        return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roles = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        //封装返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RouterVo> getRouters(){
        //查询menu 结果是tree类型
        Long userId = SecurityUtils.getUserId();
        List<MenuVo> menus = menuService.selectRouterMenuTreeById(userId);
        //封装数据返回
        return ResponseResult.okResult(new RouterVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }
}