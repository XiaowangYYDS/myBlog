package com.teng.domin.vo;

import com.teng.domin.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:xiaowang
 * @Data:2022/09/30/18:39 Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterVo {

    private List<MenuVo> menus;
}
