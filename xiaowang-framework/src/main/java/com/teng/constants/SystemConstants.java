package com.teng.constants;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/8:26 Description:
 */
public class SystemConstants {
    /*
    1 、文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /*
    2、文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /*
    分页current大小
     */
    public static final long PAGE_CURRENT = 1;
    /*
    每页最大条数
     */
    public static final long PAGE_SIZE = 10;

    /**
     *
     */
    public static final String STATUS_NORMAL = "0";

    public static final String STATUS_DRAFT = "1";

    /**
     * 友链状态为审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 请求头名称
     */
    public static final String REQUEST_HEADER = "token";
    /**
     * 评论根id
     */
    public static final long COMMENT_ROOT_ID = -1;
    /**
     * 评论类型为文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * Oss文件类别1、用户头像
     */
    public static final String OSS_FILE_AVATAR = "0";

    /**
     * Oss文件类别2、文章缩略图
     */
    public static final String OSS_FILE_THUMBNAIL = "1";

    /**
     * redis中文章浏览量的key
     */
    public static final String ARTICLE_VIEW_COUNT = "article:viewCount";

    /**
     * menu_type为C（菜单）
     */
    public static final String MENU_TYPE_C = "C";
    /**
     *  menu_type为F（按钮）
     */
    public static final String MENU_TYPE_BUTTON = "F";
    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 后台用户
     */
    public static final String ADMIN = "1";
}
