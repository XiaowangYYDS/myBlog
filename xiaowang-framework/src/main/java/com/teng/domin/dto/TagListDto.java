package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/01/15:29 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {
    //标签名
    private String name;
    //标签类型
    private String remark;
}
