package com.teng.service.impl;

import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import com.teng.service.UploadService;
import com.teng.utils.AliyunOssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/14:30 Description:
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private AliyunOssUtils aliyunOssUtils;

    @Override
    public ResponseResult uploadAvatar(MultipartFile file) {


        //判断文件类型和文件大小
        String filename = file.getOriginalFilename();
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERRO);
        }
        String imgUrl = null;
        try {
            //上传到aliyunOss
            imgUrl = aliyunOssUtils.ossUpload(filename, SystemConstants.OSS_FILE_AVATAR,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.okResult(imgUrl);
    }

    @Override
    public ResponseResult uploadThumbnail(MultipartFile file) {
        //上传到aliyunOss
        String filename = file.getOriginalFilename();
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERRO);
        }
        String imgUrl = null;
        try {
            imgUrl = aliyunOssUtils.ossUpload(filename, SystemConstants.OSS_FILE_THUMBNAIL,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.okResult(imgUrl);
    }
}
