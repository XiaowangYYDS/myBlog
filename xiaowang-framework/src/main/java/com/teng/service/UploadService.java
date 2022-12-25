package com.teng.service;

import com.teng.domin.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/14:29 Description:
 */
public interface UploadService {

    ResponseResult uploadAvatar(MultipartFile file);

    ResponseResult uploadThumbnail(MultipartFile file);
}
