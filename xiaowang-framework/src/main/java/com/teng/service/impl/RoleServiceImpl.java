package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.AddRoleDto;
import com.teng.domin.dto.RoleListDto;
import com.teng.domin.dto.RoleStatusDto;
import com.teng.domin.dto.UpdateRoleDto;
import com.teng.domin.entity.Role;
import com.teng.domin.entity.RoleMenu;
import com.teng.domin.vo.PageVo;
import com.teng.domin.vo.RoleListVo;
import com.teng.domin.vo.RoleVo;
import com.teng.mapper.RoleMapper;
import com.teng.service.RoleMenuService;
import com.teng.service.RoleService;
import com.teng.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 14:49:47
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回的集合中只需要又admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询当前用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()),Role::getRoleName,roleListDto.getRoleName());
        queryWrapper.like(StringUtils.hasText(roleListDto.getStatus()),Role::getStatus,roleListDto.getStatus());
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        List<Role> records = page.getRecords();
        List<RoleListVo> roleListVos = BeanCopyUtil.copyBeanList(records, RoleListVo.class);
        return ResponseResult.okResult(new PageVo(roleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult updateRoleStatus(RoleStatusDto roleStatusDto) {
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtil.copyBean(addRoleDto, Role.class);
        save(role);
        System.out.println(role.getId());
        if (addRoleDto.getMenuIds() != null && addRoleDto.getMenuIds().size() > 0){
            addRoleMenu(role);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtil.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        Role role = BeanCopyUtil.copyBean(updateRoleDto, Role.class);
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(updateRoleDto.getId());
        addRoleMenu(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(List<Long> id) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Role::getId,id);
        remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.NORMAL);
        List<Role> list = list(queryWrapper);
        List<RoleListVo> roleListVos = BeanCopyUtil.copyBeanList(list, RoleListVo.class);
        return ResponseResult.okResult(roleListVos);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);
    }

    private void addRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = role.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }
}
