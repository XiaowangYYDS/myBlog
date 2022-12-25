package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/01/16:08 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagUpdateDto {

    private Long id;
    private String name;
    private String remark;
}
