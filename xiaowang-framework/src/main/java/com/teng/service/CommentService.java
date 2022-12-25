package com.teng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.CommentDto;
import com.teng.domin.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-09-26 12:42:20
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(CommentDto commentDto);
}
