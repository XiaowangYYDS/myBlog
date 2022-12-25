package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/20:22 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserVo {
    //主键
    private Long id;
    //昵称
    private String nickName;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //用户状态
    private String status;

    private List<Long> roleIds;
}
