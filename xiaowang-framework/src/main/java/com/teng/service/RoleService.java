package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.AddRoleDto;
import com.teng.domin.dto.RoleListDto;
import com.teng.domin.dto.RoleStatusDto;
import com.teng.domin.dto.UpdateRoleDto;
import com.teng.domin.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-09-30 14:49:47
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    ResponseResult updateRoleStatus(RoleStatusDto roleStatusDto);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(List<Long> id);

    ResponseResult listAllRole();

    List<Long> getRoleIdsByUserId(Long userId);
}
