package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.ArticleDto;
import com.teng.domin.dto.ArticleListDto;
import com.teng.domin.entity.Article;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/09/20/16:33 Description:
 */
public interface ArticleService extends IService<Article> {

    //查询热门文章
    ResponseResult getHotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(ArticleDto addArticleDto);

    ResponseResult selectArticlePage(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult getArticleDetailById(Long id);

    ResponseResult updateArticle(ArticleDto articleDto);

    ResponseResult deleteArticle(List<Long> id);
}
