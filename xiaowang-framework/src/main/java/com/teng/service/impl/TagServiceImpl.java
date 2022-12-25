package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.TagListDto;
import com.teng.domin.dto.TagUpdateDto;
import com.teng.domin.entity.Tag;
import com.teng.domin.vo.PageVo;
import com.teng.domin.vo.TagVo;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import com.teng.mapper.TagMapper;
import com.teng.service.TagService;
import com.teng.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-09-29 19:54:51
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, wrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagDto) {
        if (!StringUtils.hasText(tagDto.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(tagDto.getRemark())){
            throw new SystemException(AppHttpCodeEnum.TAGREMARK_NOT_NULL);
        }
        Tag tag = BeanCopyUtil.copyBean(tagDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long tagId) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId,tagId);
        Tag tag = tagMapper.selectOne(wrapper);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult updateTagById(TagUpdateDto tagUpdateDto) {
        if (!StringUtils.hasText(tagUpdateDto.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(tagUpdateDto.getRemark())){
            throw new SystemException(AppHttpCodeEnum.TAGREMARK_NOT_NULL);
        }
        Tag tag = BeanCopyUtil.copyBean(tagUpdateDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(List<Long> id) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Tag::getId,id);
        remove(wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> tags = list(wrapper);
        List<TagVo> tagVoList = BeanCopyUtil.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVoList);
    }
}
