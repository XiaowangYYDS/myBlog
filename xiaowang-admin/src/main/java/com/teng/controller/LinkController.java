package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.domin.dto.LinkDto;
import com.teng.domin.dto.LinkListDto;
import com.teng.domin.dto.LinkStatusDto;
import com.teng.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/03/12:43 Description:
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;


    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return linkService.linkPageList(pageNum,pageSize,linkListDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkDto linkDto){
        return linkService.addLink(linkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkInfo(@PathVariable("id") Long id){
        return linkService.getLinkInfo(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkDto linkDto){
        return linkService.updateLink(linkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") List<Long> id){
        return linkService.deleteLink(id);
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody LinkStatusDto linkStatusDto){
        return linkService.changeLinkStatus(linkStatusDto);
    }
}
