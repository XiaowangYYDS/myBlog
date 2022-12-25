package com.teng.domin.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/15:43 Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {

    //@TableId
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
    private String status;
}
