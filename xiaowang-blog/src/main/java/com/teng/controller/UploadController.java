package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.service.UploadService;
import com.teng.utils.AliyunOssUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/14:21 Description:
 */
@RestController
@Api(tags = "文件上传",description = "文件上传相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadAvatar(@RequestParam("img") MultipartFile file){
        return uploadService.uploadAvatar(file);
    }
}
