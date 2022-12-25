package com.teng.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.CategoryDto;
import com.teng.domin.dto.CategoryListDto;
import com.teng.domin.dto.CategoryStatusDto;
import com.teng.domin.vo.CategoryVo;
import com.teng.domin.vo.ExcelCategoryVo;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.service.CategoryService;
import com.teng.utils.BeanCopyUtil;
import com.teng.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/01/17:05 Description:
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


    @PreAuthorize("@ps.hasPermissions('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            ResponseResult result = categoryService.listAllCategory();
            List<CategoryVo> categoryVoList = (List<CategoryVo>) result.getData();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtil.copyBeanList(categoryVoList, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).sheet("分类导出").doWrite(excelCategoryVos);
        }catch (UnsupportedEncodingException e) {
            //如果出现异常也要响应
            //重置response
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult getCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.getCategoryPage(pageNum,pageSize,categoryListDto);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") List<Long> id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeCategoryStatus(@RequestBody CategoryStatusDto categoryStatusDto){
        return categoryService.changeCategoryStatus(categoryStatusDto);
    }
}
