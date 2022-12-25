package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.dto.MenuDto;
import com.teng.domin.dto.MenuListDto;
import com.teng.domin.entity.Menu;
import com.teng.domin.vo.MenuTreeVo;
import com.teng.domin.vo.RoleMenuTreeSelectVo;
import com.teng.service.MenuService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/12:53 Description:
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult menuList(MenuListDto menuListDto){
        return menuService.menuList(menuListDto);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody MenuDto menuDto){
        Menu menu = BeanCopyUtil.copyBean(menuDto, Menu.class);
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = (List<Menu>) menuService.menuList(new MenuListDto()).getData();
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = (List<Menu>) menuService.menuList(new MenuListDto()).getData();
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
}
