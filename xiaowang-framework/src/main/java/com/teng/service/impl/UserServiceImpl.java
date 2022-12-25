package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.*;
import com.teng.domin.entity.Role;
import com.teng.domin.entity.User;
import com.teng.domin.entity.UserRole;
import com.teng.domin.vo.*;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import com.teng.mapper.UserMapper;
import com.teng.service.RoleService;
import com.teng.service.UserRoleService;
import com.teng.service.UserService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.CheckUtil;
import com.teng.utils.RedisCache;
import com.teng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-09-25 16:17:29
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult userinfo() {
        //获取当前用户Id
        Long userId = SecurityUtils.getUserId();
        //根据用户Id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserInfoDto userInfoDto) {
        User user = BeanCopyUtil.copyBean(userInfoDto, User.class);
        Long userId = SecurityUtils.getUserId();
        user.setId(userId);
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(RegisterDto registerDto) {
        //对数据进行非空判断
        if (!StringUtils.hasText(registerDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(registerDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(registerDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(registerDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        //校验邮箱格式是否合法
        boolean emailCheck = CheckUtil.emailCheck(registerDto.getEmail());
        if (!emailCheck){
            throw new SystemException(AppHttpCodeEnum.EMAIL_LAWFUL);
        }
        //对数据进行是否重复判断
        if (userNameExit(registerDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExit(registerDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIT);
        }
        if (emailExit(registerDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //将registerDto复制给user对象
        User user = BeanCopyUtil.copyBean(registerDto, User.class);
        //对密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()),User::getUserName,userListDto.getUserName());
        queryWrapper.like(StringUtils.hasText(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        queryWrapper.like(StringUtils.hasText(userListDto.getStatus()),User::getStatus,userListDto.getStatus());
        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        List<UserListVo> userListVos = BeanCopyUtil.copyBeanList(page.getRecords(), UserListVo.class);
        return ResponseResult.okResult(new PageVo(userListVos,page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult addUser(AddUserDto addUserDto) {
        if (emailExit(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (phoneNumExit(addUserDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (userNameExit(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExit(addUserDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIT);
        }
        if (!emailCheck(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_LAWFUL);
        }
        if (!phoneNumCheck(addUserDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUM_LAWFUL);
        }
        User user = BeanCopyUtil.copyBean(addUserDto, User.class);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        save(user);
        if (addUserDto.getRoleIds() != null && addUserDto.getRoleIds().size() > 0){
            addUserRole(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteUserById(List<Long> id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getId,id);
        remove(queryWrapper);
        //根据用户id删除该用户与角色的关联
        deleteUserRole(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfoAndRole(Long userId) {
        User user = getById(userId);
        UserVo userVo = BeanCopyUtil.copyBean(user, UserVo.class);
        List<RoleListVo> roles = (List<RoleListVo>) roleService.listAllRole().getData();
        //查询当前用户所具有的角色Id
        List<Long> roleIds = roleService.getRoleIdsByUserId(userId);
        return ResponseResult.okResult(new UserInfoAndRoleVo(userVo,roleIds,roles));
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UpdateUserVo updateUserVo) {
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId,updateUserVo.getId());
        //删除用户与角色原有的关联
        userRoleService.remove(lambdaQueryWrapper);
        User user = BeanCopyUtil.copyBean(updateUserVo, User.class);
        if (updateUserVo.getRoleIds() != null && updateUserVo.getRoleIds().size() > 0){
            addUserRole(user);
        }
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeUserStatusById(UserStatusDto userStatusDto) {
        User user = new User();
        user.setId(userStatusDto.getUserId());
        user.setStatus(userStatusDto.getStatus());
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult resetPassword(PasswordDto passwordDto) {
        if (!StringUtils.hasText(passwordDto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        User user = new User();
        user.setId(passwordDto.getUserId());
        String password = passwordEncoder.encode(passwordDto.getPassword());
        user.setPassword(password);
        updateById(user);
        redisCache.deleteObject("blogLogin:" + passwordDto.getUserId());
        return ResponseResult.okResult();
    }

    private void deleteUserRole(List<Long> id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(UserRole::getUserId,id);
        userRoleService.remove(queryWrapper);
    }

    /**
     * 添加用户角色关联
     * @param user 用户实体类
     */
    private void addUserRole(User user) {
        List<UserRole> userRoleList = user.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }


    /**
     * 判断用户用户名是否已经存在
     * @param userName 用户名
     * @return
     */
    private boolean userNameExit(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }

    /**
     * 判断用户邮箱是否已经存在
     * @param email 邮箱号
     * @return
     */
    private boolean emailExit(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper) > 0;
    }

    /**
     * 判断用户昵称是否已经存在
     * @param nickName 昵称
     * @return
     */
    private boolean nickNameExit(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper) > 0;
    }

    /**
     * 判断电话号是否已经存在
     * @param phoneNum 电话号
     * @return
     */
    private boolean phoneNumExit(String phoneNum){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phoneNum);
        return count(queryWrapper) > 0;
    }

    /**
     * 检查邮箱格式是否合法
     * @param email 邮箱号
     * @return
     */
    private boolean emailCheck(String email){
        return CheckUtil.emailCheck(email);
    }

    private boolean phoneNumCheck(String phoneNum){
        return CheckUtil.phoneCheck(phoneNum);
    }
}
