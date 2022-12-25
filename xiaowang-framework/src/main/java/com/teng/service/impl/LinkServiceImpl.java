package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.LinkDto;
import com.teng.domin.dto.LinkListDto;
import com.teng.domin.dto.LinkStatusDto;
import com.teng.domin.entity.Link;
import com.teng.domin.vo.LinkVo;
import com.teng.domin.vo.PageVo;
import com.teng.mapper.LinkMapper;
import com.teng.service.LinkService;
import com.teng.utils.BeanCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-09-25 15:34:34
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> linkQueryWrapper = new LambdaQueryWrapper<>();
        linkQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(linkQueryWrapper);

        //封装Vo
        List<LinkVo> linkVos = BeanCopyUtil.copyBeanList(linkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkPageList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkListDto.getName()),Link::getName,linkListDto.getName());
        queryWrapper.like(StringUtils.hasText(linkListDto.getStatus()),Link::getStatus,linkListDto.getStatus());
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtil.copyBeanList(page.getRecords(), LinkVo.class);
        return ResponseResult.okResult(new PageVo(linkVos,page.getTotal()));
    }

    @Override
    public ResponseResult addLink(LinkDto linkDto) {
        Link link = BeanCopyUtil.copyBean(linkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkInfo(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtil.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkDto linkDto) {
        Link link = BeanCopyUtil.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Long> id) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Link::getId,id);
        remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto) {
        Link link = BeanCopyUtil.copyBean(linkStatusDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }
}
