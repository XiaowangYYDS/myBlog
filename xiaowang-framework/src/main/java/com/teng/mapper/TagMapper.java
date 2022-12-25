package com.teng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teng.domin.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-29 19:54:51
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
