package com.teng.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/12:38 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    private List rows;

    private Long total;
}
