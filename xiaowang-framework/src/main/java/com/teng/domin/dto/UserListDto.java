package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/02/17:27 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {

    private String userName;
    private String phonenumber;
    private String status;
}
