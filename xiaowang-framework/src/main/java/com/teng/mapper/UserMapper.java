package com.teng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teng.domin.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-25 16:17:29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
