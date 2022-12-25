package com.teng.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/20:02 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoAndRoleVo {
    private UserVo user;
    private List<Long> roleIds;
    private List<RoleListVo> roles;
}
