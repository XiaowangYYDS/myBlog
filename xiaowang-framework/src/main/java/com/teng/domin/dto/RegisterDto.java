package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/16:59 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //邮箱
    private String email;
    //密码
    private String password;
}
