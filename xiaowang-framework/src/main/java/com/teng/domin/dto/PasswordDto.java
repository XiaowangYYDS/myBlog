package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/03/18:02 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {

    private Long userId;
    private String password;
}
