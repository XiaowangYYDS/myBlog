package com.teng.domin.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(XwArticleTag)表实体类
 *
 * @author makejava
 * @since 2022-10-01 19:05:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xw_article_tag")
public class ArticleTag {
    //文章id@TableId
    private Long articleId;
    //标签id@TableId
    private Long tagId;




}
