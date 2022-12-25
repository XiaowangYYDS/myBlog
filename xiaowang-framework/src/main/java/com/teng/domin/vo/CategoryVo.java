package com.teng.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/10:09 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {

    //类别id
    private Long id;
    //类别名
    private String name;

    private String description;

    private String status;
}
