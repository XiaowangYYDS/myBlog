import com.teng.XiaoWangBlogApplication;
import com.teng.constants.SystemConstants;
import com.teng.domin.ResponseResult;
import com.teng.domin.entity.AliyunOss;
import com.teng.domin.entity.Article;
import com.teng.domin.vo.ArticleListVo;
import com.teng.domin.vo.PageVo;
import com.teng.service.ArticleService;
import com.teng.utils.AliyunOssUtils;
import com.teng.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author:xiaowang
 * @Data:2022/09/27/13:47 Description:
 */
@SpringBootTest(classes = XiaoWangBlogApplication.class)
class OssUploadTest {

    @Resource
    private AliyunOssUtils aliyunOssUtils;
    @Autowired
    private AliyunOss aliyunOss;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void ossUploadTest() throws FileNotFoundException {
        String filePath = "D:\\1.png";
        String fileName = "1.png";
        InputStream inputStream = new FileInputStream(filePath);
        String s = aliyunOssUtils.ossUpload(fileName, SystemConstants.OSS_FILE_AVATAR, inputStream);
        System.out.println(s);
    }

    @Test
    void aliyun(){
        System.out.println(aliyunOss);
    }

    @Test
    void viewCountTest(){
        ResponseResult result = articleService.articleList(1, 10, null);
        PageVo data = (PageVo) result.getData();
        List<ArticleListVo> rows = data.getRows();
        for (ArticleListVo row : rows) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT, row.getId().toString());
            row.setViewCount(viewCount.longValue());
        }
        System.out.println(rows);
    }


    @Test
    void passwordEncoding(){
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

}
