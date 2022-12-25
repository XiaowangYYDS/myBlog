package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/12:54 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuListDto {
    private String menuName;
    private String status;
}
