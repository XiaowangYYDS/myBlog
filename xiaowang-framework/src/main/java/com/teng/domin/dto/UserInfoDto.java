package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/15:36 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private Long id;
    //昵称
    private String nickName;
    //邮箱
    private String email;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
}
