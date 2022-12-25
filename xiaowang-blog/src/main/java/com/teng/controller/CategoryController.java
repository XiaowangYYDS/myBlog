package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/9:49 Description:
 */
@RestController
@RequestMapping("/category")
@Api(tags = "文章类别",description = "文章类别相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
