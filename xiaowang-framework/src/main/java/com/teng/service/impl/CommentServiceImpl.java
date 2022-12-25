package com.teng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.dto.CommentDto;
import com.teng.domin.entity.Comment;
import com.teng.domin.vo.CommentVo;
import com.teng.domin.vo.PageVo;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import com.teng.mapper.CommentMapper;
import com.teng.service.CommentService;
import com.teng.service.UserService;
import com.teng.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-09-26 12:42:20
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对应的articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论root_id=-1
        queryWrapper.eq(Comment::getRootId,SystemConstants.COMMENT_ROOT_ID);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        //根据创建时间升序
        queryWrapper.orderByAsc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(pageNum,pageSize);
        page(commentPage,queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(commentPage.getRecords());
        //查询所有根评论对应的子评论集合并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList, commentPage.getTotal()));
    }

    @Override
    public ResponseResult addComment(CommentDto commentDto) {
        //评论内容不能为空
        if (!StringUtils.hasText(commentDto.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Comment comment = BeanCopyUtil.copyBean(commentDto, Comment.class);
        save(comment);
        return ResponseResult.okResult();
    }


    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getRootId, id);
        lambdaQueryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(lambdaQueryWrapper);
        List<CommentVo> commentVos = toCommentVoList(list);
        return commentVos;
    }


    private List<CommentVo> toCommentVoList(List<Comment> commentList){
        List<CommentVo> commentVoList =
                BeanCopyUtil.copyBeanList(commentList,CommentVo.class);
        //遍历Vo集合
        for (CommentVo commentVo : commentVoList) {
            //通过createBy查询用用户昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过createBy查询用户头像并赋值
            String avatar = userService.getById(commentVo.getCreateBy()).getAvatar();
            commentVo.setAvatar(avatar);
            //通过toCommentUserId查询用用户昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != -1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        return commentVoList;
    }
}
