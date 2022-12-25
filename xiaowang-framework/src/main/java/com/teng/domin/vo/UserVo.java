package com.teng.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/20:06 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
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
}
