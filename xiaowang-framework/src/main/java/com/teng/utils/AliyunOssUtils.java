package com.teng.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.teng.constants.SystemConstants;
import com.teng.domin.entity.AliyunOss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/13:17 Description:
 */
@Component
public class AliyunOssUtils {

    @Autowired
    private AliyunOss aliyunOss;

    public String ossUpload(String fileName,String fileType, InputStream inputStream){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = aliyunOss.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = aliyunOss.getAccessKeyId();
        String accessKeySecret = aliyunOss.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = aliyunOss.getBucketName();
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String ossFilePath = null;
        if (fileType.equals(SystemConstants.OSS_FILE_AVATAR)){
            ossFilePath = "userAvatar/";
        }else if (fileType.equals(SystemConstants.OSS_FILE_THUMBNAIL)){
            ossFilePath = "blogThumbnail/";
        }

        //生成随机Id
        String uuid = UUID.randomUUID().toString();
        //获取文件扩展名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String objectName = ossFilePath + uuid + fileExtension;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return aliyunOss.getUrl() + objectName;
    }
}
