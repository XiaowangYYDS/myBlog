package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.TagListDto;
import com.teng.domin.dto.TagUpdateDto;
import com.teng.domin.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-09-29 19:54:51
 */
public interface TagService extends IService<Tag> {

    ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagDto);

    ResponseResult getTagById(Long tagId);

    ResponseResult updateTagById(TagUpdateDto tagUpdateDto);

    ResponseResult deleteTag(List<Long> id);

    ResponseResult listAllTag();
}
