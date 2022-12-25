package com.teng.domin.vo;

import com.mysql.cj.log.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:xiaowang
 * @Data:2022/09/28/16:43 Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewCountVo {

    private Long id;
    private Long viewCount;
}
