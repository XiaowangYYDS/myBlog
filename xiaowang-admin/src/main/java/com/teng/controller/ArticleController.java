package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.dto.ArticleDto;
import com.teng.domin.dto.ArticleListDto;
import com.teng.domin.entity.Article;
import com.teng.domin.entity.Menu;
import com.teng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/01/18:59 Description:
 */
@RestControllerAdvice
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody ArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.selectArticlePage(pageNum,pageSize,articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetailById(@PathVariable("id") Long id){
        return articleService.getArticleDetailById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id")List<Long> id){
        return articleService.deleteArticle(id);
    }


}
