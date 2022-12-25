package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.LinkDto;
import com.teng.domin.dto.LinkListDto;
import com.teng.domin.dto.LinkStatusDto;
import com.teng.domin.entity.Link;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-09-25 15:34:34
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult linkPageList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult addLink(LinkDto linkDto);

    ResponseResult getLinkInfo(Long id);

    ResponseResult updateLink(LinkDto linkDto);

    ResponseResult deleteLink(List<Long> id);

    ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto);
}
