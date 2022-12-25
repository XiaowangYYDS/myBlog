package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2022-10-02 16:31:46
 */
public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}
