package com.teng.controller;

import com.mysql.cj.log.Log;
import com.teng.domin.ResponseResult;
import com.teng.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author:xiaowang
 * @Data:2022/09/20/16:37 Description:
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章相关接口")
public class ArticleController {

    @Resource
    private ArticleService articleService;
//    @GetMapping("/test")
//    public String test(){
//        return "ok";
//    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章 封装成ResponseResult返回
         ResponseResult result = articleService.getHotArticleList();
         return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList( Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
