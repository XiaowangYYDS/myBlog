package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/09/29/20:19 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    //用户名
    private String userName;
    //密码
    private String password;
}
