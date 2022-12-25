package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.CategoryDto;
import com.teng.domin.dto.CategoryListDto;
import com.teng.domin.dto.CategoryStatusDto;
import com.teng.domin.entity.Category;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-09-25 09:36:20
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult getCategoryPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult addCategory(CategoryDto addCategoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult deleteCategory(List<Long> id);

    ResponseResult changeCategoryStatus(CategoryStatusDto categoryStatusDto);
}
