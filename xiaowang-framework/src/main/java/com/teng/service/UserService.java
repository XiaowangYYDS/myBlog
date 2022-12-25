package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.*;
import com.teng.domin.entity.User;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-09-25 16:17:29
 */
public interface UserService extends IService<User> {

    ResponseResult userinfo();

    ResponseResult updateUserInfo(UserInfoDto userInfoDto);

    ResponseResult register(RegisterDto registerDto);

    ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUserById(List<Long> id);

    ResponseResult getUserInfoAndRole(Long userId);

    ResponseResult updateUser(UpdateUserVo updateUserVo);

    ResponseResult changeUserStatusById(UserStatusDto userStatusDto);

    ResponseResult resetPassword(PasswordDto passwordDto);
}
