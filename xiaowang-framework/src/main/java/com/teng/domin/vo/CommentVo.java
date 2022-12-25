package com.teng.domin.vo;

import com.mysql.cj.log.Log;
import com.teng.domin.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/09/26/13:14 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;
    private Long createBy;
    private String username;
    private Date createTime;
    private String avatar;

    //子评论集合
    private List<CommentVo> children;
}
