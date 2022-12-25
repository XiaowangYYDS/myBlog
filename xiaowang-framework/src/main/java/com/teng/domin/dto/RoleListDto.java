package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/13:42 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleListDto {
    private String roleName;
    //角色状态（0正常 1停用）
    private String status;
}
