package com.teng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teng.domin.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-30 14:49:47
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    List<Long> selectRoleIdByUserId(Long userId);
}
