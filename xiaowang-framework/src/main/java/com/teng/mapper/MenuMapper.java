package com.teng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teng.domin.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-30 09:56:44
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeById(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}
