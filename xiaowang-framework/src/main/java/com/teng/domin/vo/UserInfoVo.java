package com.teng.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/20:25 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {

    //主键@TableId
    private Long id;
    //昵称
    private String nickName;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
}
