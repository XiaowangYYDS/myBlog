import com.teng.constants.SystemConstants;
import com.teng.utils.AliyunOssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/13:47 Description:
 */
@SpringBootTest
class OssUploadTest {

    @Resource
    private AliyunOssUtils aliyunOssUtils;

    @Resource
    private

    @Test
    void ossUploadTest() throws FileNotFoundException {
        String filePath = "D:\\1.png";
        String fileName = "1.png";
        InputStream inputStream = new FileInputStream(filePath);
        String s = aliyunOssUtils.ossUpload(fileName, SystemConstants.OSS_FILE_AVATAR, inputStream);
        System.out.println(s);
    }


}
