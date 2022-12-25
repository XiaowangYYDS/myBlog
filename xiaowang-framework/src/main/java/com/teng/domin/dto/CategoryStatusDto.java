package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/03/12:23 Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatusDto {

    private Long categoryId;
    private String status;
}
