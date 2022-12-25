package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/03/9:36 Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusDto {
    private Long userId;
    private String status;
}
