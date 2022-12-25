package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.ArticleDto;
import com.teng.domin.dto.ArticleListDto;
import com.teng.domin.entity.ArticleTag;
import com.teng.domin.vo.*;
import com.teng.domin.entity.Category;
import com.teng.mapper.ArticleMapper;
import com.teng.domin.entity.Article;
import com.teng.service.ArticleService;
import com.teng.service.ArticleTagService;
import com.teng.service.CategoryService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author:xiaowang
 * @Data:2022/09/20/16:34 Description:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult getHotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryChainWrapper = new LambdaQueryWrapper<>();
        //1、正式发布的文章

        queryChainWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //   2、按照浏览量进行排序
        queryChainWrapper.orderByDesc(Article::getViewCount);
        //   3、最多只查询10条
        Page<Article> page = new Page(SystemConstants.PAGE_CURRENT,SystemConstants.PAGE_SIZE);
        page(page,queryChainWrapper);
        List<Article> articles = page.getRecords();
        //bean拷贝
        List<HotArticleVo> hotArticleVos = BeanCopyUtil.copyBeanList(articles, HotArticleVo.class);
        for (HotArticleVo hotArticleVo : hotArticleVos) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, hotArticleVo.getId().toString());
            hotArticleVo.setViewCount(viewCount.longValue());
        }
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        //如果 有categoryId 就要查询时要和传入的相同
        articleWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        //状态是真实发布的
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //置顶的文章，显示在最前面（对isTop进行降序）
        articleWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> articlePage = new Page<>(pageNum,pageSize);
        page(articlePage,articleWrapper);

        List<Article> articles = articlePage.getRecords();
        //查询categoryName
        articles = articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        //获取分类Id查询分类信息，获取分类名称
                        Category category = categoryService.getById(article.getCategoryId());
                        //把分类名称设置给Article
                        article.setCategoryName(category.getName());
                        return article;
                    }
                })
                .collect(Collectors.toList());
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //封装Vo
        List<ArticleListVo> articleListVos = BeanCopyUtil.copyBeanList(articlePage.getRecords(), ArticleListVo.class);
        for (ArticleListVo articleListVo : articleListVos) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, articleListVo.getId().toString());
            articleListVo.setViewCount(viewCount.longValue());
        }
        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, id.toString());
        if (viewCount != null){
            article.setViewCount(viewCount.longValue());
        }
        //根据分类id查询类别名
        ArticleDetail articleDetail = BeanCopyUtil.copyBean(article, ArticleDetail.class);
        //封装响应返回
        Category category = categoryService.getById(articleDetail.getCategoryId());
        if (category != null){
            articleDetail.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetail);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //获取redis中的浏览量map
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT);
        //获得文章的浏览量
        try {
            viewCountMap.get(id.toString());
            //更新redis中对应 id的浏览量
            redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,id.toString(),1);
        } catch (Exception e) {
            redisCache.setCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,id.toString(),1);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(ArticleDto addArticleDto) {
        if (addArticleDto.getCategoryId() == null){
            return ResponseResult.errorResult(500,"请选择文章类别");
        }
        if (addArticleDto.getTags().size() == 0){
            return ResponseResult.errorResult(500,"请选择文章标签");
        }
        //添加博客
        Article article = BeanCopyUtil.copyBean(addArticleDto, Article.class);
        save(article);
        //添加文章后将文章浏览量存储redis
        redisCache.setCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,article.getId().toString(),0);
        //获得标签集合
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加文章和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectArticlePage(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(articleListDto.getTitle()),Article::getTitle,articleListDto.getTitle());
        wrapper.like(StringUtils.hasText(articleListDto.getSummary()),Article::getSummary,articleListDto.getSummary());
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        //将数据转换成Vo
        List<Article> articles = page.getRecords();
        List<ArticlePageVo> articlePageVos = BeanCopyUtil.copyBeanList(articles, ArticlePageVo.class);
        return ResponseResult.okResult(new PageVo(articlePageVos,page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetailById(Long id) {
        Article article = getById(id);
        //获取关联标签
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(wrapper);
        List<Long> tagIds = articleTags.stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());
        //封装成Vo返回
        ArticleVo articleVo = BeanCopyUtil.copyBean(article, ArticleVo.class);
        articleVo.setTags(tagIds);
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateArticle(ArticleDto articleDto) {
        if (articleDto.getStatus().equals("1")){
            redisCache.delCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,articleDto.getId().toString());
        }
        Article article = BeanCopyUtil.copyBean(articleDto, Article.class);
        //更新文章
        updateById(article);
        //删除原有的 标签和博客的关联
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(queryWrapper);
        //添加新的博客和标签的关联信息
        List<ArticleTag> tags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(tags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(List<Long> id) {
        for (Long articleId : id) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, articleId.toString());
            if (viewCount != null){
                redisCache.delCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,articleId.toString());
            }
        }
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Article::getId,id);
        remove(wrapper);
        return ResponseResult.okResult();
    }

}
