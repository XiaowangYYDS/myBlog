package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.CategoryDto;
import com.teng.domin.dto.CategoryListDto;
import com.teng.domin.dto.CategoryStatusDto;
import com.teng.domin.vo.CategoryVo;
import com.teng.domin.entity.Category;
import com.teng.domin.vo.PageVo;
import com.teng.mapper.CategoryMapper;
import com.teng.service.ArticleService;
import com.teng.service.CategoryService;
import com.teng.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-09-25 09:36:20
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    @Lazy
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        /*
        使用分步查询
         */
        //1、查询文章表状态为已发布的文章、
//        LambdaQueryWrapper<Article>  articleWrapper = new LambdaQueryWrapper<>();
//        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
//        List<Article> articleList = articleService.list();
//        //2、获取文章的分类id并且去重
//        Set<Long> categoryIds = articleList.stream()
//                .map(article -> article.getCategoryId())
//                .collect(Collectors.toSet());
//        //查询分类表
//        List<Category> categories = listByIds(categoryIds);
//        categories = categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
//                .collect(Collectors.toList());

        //查询分类集合
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.STATUS_NORMAL);
        List<Category> categories = list(queryWrapper);
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtil.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,SystemConstants.NORMAL);
        List<Category> categories = list(wrapper);
        List<CategoryVo> categoryVoList = BeanCopyUtil.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVoList);
    }

    @Override
    public ResponseResult getCategoryPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryListDto.getName()),Category::getName,categoryListDto.getName());
        queryWrapper.like(StringUtils.hasText(categoryListDto.getStatus()),Category::getStatus,categoryListDto.getStatus());
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Category> categories = page.getRecords();
        List<CategoryVo> categoryVoList = BeanCopyUtil.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(new PageVo(categoryVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(CategoryDto addCategoryDto) {
        Category category = BeanCopyUtil.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtil.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtil.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(List<Long> id) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Category::getId,id);
        remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeCategoryStatus(CategoryStatusDto categoryStatusDto) {
        Category category = new Category();
        category.setId(categoryStatusDto.getCategoryId());
        category.setStatus(categoryStatusDto.getStatus());
        updateById(category);
        return ResponseResult.okResult();
    }
}
