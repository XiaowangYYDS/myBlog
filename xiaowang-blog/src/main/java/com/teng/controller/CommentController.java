package com.teng.controller;

import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.CommentDto;
import com.teng.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:xiaowang
 * @Data:2022/09/26/12:47 Description:
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation(value = "评论列表",notes = "根据文章id获取评论")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "articleId",value = "文章id"),
        @ApiImplicitParam(name = "pageNum",value = "页码"),
        @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,
                                      Integer pageSize){
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody CommentDto commentDto){
        return commentService.addComment(commentDto);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.getCommentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
