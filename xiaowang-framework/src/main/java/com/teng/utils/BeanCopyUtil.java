package com.teng.utils;

import com.teng.domin.entity.Article;
import com.teng.domin.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/8:34 Description:
 */
public class BeanCopyUtil {
    private BeanCopyUtil(){

    }

    //单个bean拷贝
    public static <V> V copyBean(Object source,Class<V> clazz) {

        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    //bean集合拷贝
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        //使用Stream流的形式
        //将集合转换为流
        return list.stream()
                .map(new Function<O, V>() {
                    @Override
                    public V apply(O o) {
                        return copyBean(o, clazz);
                    }
                })
                .collect(Collectors.toList());
    }





}
