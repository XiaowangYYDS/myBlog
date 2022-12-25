package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.dto.TagListDto;
import com.teng.domin.dto.TagUpdateDto;
import com.teng.domin.entity.Tag;
import com.teng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/09/29/19:58 Description:
 */
@RestController
@RequestMapping("/content")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,
                               TagListDto tagListDto){
        return tagService.getTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping("/tag")
    public ResponseResult addTag(@RequestBody TagListDto tagDto){
        return tagService.addTag(tagDto);
    }

    @GetMapping("/tag/{id}")
    public ResponseResult getTagById(@PathVariable("id") Long tagId){
        return tagService.getTagById(tagId);
    }

    @PutMapping("/tag")
    public ResponseResult updateTagById(@RequestBody TagUpdateDto tagUpdateDto){
        return tagService.updateTagById(tagUpdateDto);
    }

    @DeleteMapping("/tag/{id}")
    public ResponseResult deleteTag(@PathVariable("id") List<Long> id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/tag/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

}
