package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/9:51 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDto {

    //文章标题
    private String title;

    //文章摘要
    private String summary;
}
