package com.teng.domin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/13:14 Description:
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliyunOss {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String bucketName;

    private String url;
}
