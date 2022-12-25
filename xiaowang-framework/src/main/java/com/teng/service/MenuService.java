package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.MenuListDto;
import com.teng.domin.entity.Menu;
import com.teng.domin.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-09-30 09:56:44
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeById(Long userId);

    ResponseResult menuList(MenuListDto menuListDto);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long menuId);

    List<Long> selectMenuListByRoleId(Long roleId);
}
