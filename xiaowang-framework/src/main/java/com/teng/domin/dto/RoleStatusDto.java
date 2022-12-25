package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/13:51 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleStatusDto {

    private Long roleId;
    private String status;
}
