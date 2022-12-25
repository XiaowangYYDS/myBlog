package com.teng.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/13:46 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleListVo {
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    private Date createTime;
}
