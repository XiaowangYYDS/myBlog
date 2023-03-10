package com.teng.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/10/03/12:48 Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
    private String status;
}
