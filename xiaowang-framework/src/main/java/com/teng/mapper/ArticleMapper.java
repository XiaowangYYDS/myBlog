package com.teng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teng.domin.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:xiaowang
 * @Data:2022/09/20/16:31 Description:
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
