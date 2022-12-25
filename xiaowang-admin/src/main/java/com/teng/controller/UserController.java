package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.dto.*;
import com.teng.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/17:26 Description:
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.userList(pageNum,pageSize,userListDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        return userService.addUser(addUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUserById(@PathVariable("id") List<Long> id){
        return userService.deleteUserById(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserInfoAndRole(@PathVariable("id") Long userId){
        return userService.getUserInfoAndRole(userId);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserVo updateUserVo){
        return userService.updateUser(updateUserVo);
    }


    @PutMapping("/changeStatus")
    public ResponseResult changeUserStatusById(@RequestBody UserStatusDto userStatusDto){
        return userService.changeUserStatusById(userStatusDto);
    }

    @PutMapping("/password")
    public ResponseResult resetPassword(@RequestBody PasswordDto passwordDto){
        return userService.resetPassword(passwordDto);
    }
}
