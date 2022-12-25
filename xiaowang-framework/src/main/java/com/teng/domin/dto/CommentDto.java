package com.teng.domin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:xiaowang
 * @Data:2022/09/26/19:04 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "添加评论实体类")
public class CommentDto {

    //评论类型（0代表文章评论，1代表友链评论）
    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;
    //文章id
    @ApiModelProperty(notes = "文章id")
    private Long articleId;
    //根评论id
    @ApiModelProperty(notes = "根评论Id")
    private Long rootId;
    //评论内容
    @ApiModelProperty(notes = "评论内容")
    private String content;
    //所回复的目标评论的userid
    @ApiModelProperty(notes = "所回复的目标评论的userid")
    private Long toCommentUserId;
    //回复目标评论id
    @ApiModelProperty(notes = "回复目标评论id")
    private Long toCommentId;
    @ApiModelProperty(notes = "创建人")
    private Long createBy;
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
}
