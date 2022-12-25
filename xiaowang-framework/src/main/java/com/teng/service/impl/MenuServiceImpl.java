package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.MenuListDto;
import com.teng.domin.entity.Menu;
import com.teng.domin.vo.MenuInfoVo;
import com.teng.domin.vo.MenuVo;
import com.teng.domin.vo.PageVo;
import com.teng.mapper.MenuMapper;
import com.teng.service.MenuService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 09:56:44
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if (id == 1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_C,SystemConstants.MENU_TYPE_BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }

        //否则返回所具有的权限
        List<String> perms = getBaseMapper().selectPermsByUserId(id);
        return perms;
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeById(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            //如果是 返回所有符合要求的menu
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //否则 当前用户所具有的menu
            menus = menuMapper.selectRouterMenuTreeById(userId);
        }
        List<MenuVo> menuVos = BeanCopyUtil.copyBeanList(menus, MenuVo.class);
        //构建menuTree
        //先找出第一层的菜单 然后找他们的子菜单设置到children属性中
        List<MenuVo> menuTree = buildMenuTree(menuVos,0L);

        return menuTree;
    }

    @Override
    public ResponseResult menuList(MenuListDto menuListDto) {
        Menu menu = BeanCopyUtil.copyBean(menuListDto, Menu.class);
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(menuListDto.getMenuName()),Menu::getMenuName,menu.getMenuName());
        wrapper.like(StringUtils.hasText(menuListDto.getStatus()),Menu::getStatus,menu.getStatus());
        wrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> list = list(wrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuInfoVo menuInfoVo = BeanCopyUtil.copyBean(menu, MenuInfoVo.class);
        return ResponseResult.okResult(menuInfoVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        boolean hasChild = hasChild(menuId);
        if (hasChild){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }

    private List<MenuVo> buildMenuTree(List<MenuVo> menuVos, long parentId) {
        List<MenuVo> menuTree = menuVos.stream()
                .filter(menuVo -> menuVo.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu,menuVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数的子菜单jihe
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {
        List<MenuVo> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    public boolean hasChild(Long menuId){
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return count(queryWrapper) != 0;
    }
}
