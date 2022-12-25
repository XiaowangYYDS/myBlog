package com.teng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.domin.entity.UserRole;
import com.teng.mapper.UserRoleMapper;
import com.teng.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-10-02 17:52:04
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
