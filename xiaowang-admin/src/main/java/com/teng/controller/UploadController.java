package com.teng.controller;

import com.teng.domin.ResponseResult;
import com.teng.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:xiaowang
 * @Data:2022/10/01/17:18 Description:
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile file){
        //上传文章缩略图
        return uploadService.uploadThumbnail(file);
    }
}
