import com.teng.BlogAdminApplication;
import com.teng.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author:xiaowang
 * @Data:2022/10/01/16:16 Description:
 */
@SpringBootTest(classes = BlogAdminApplication.class)
public class DateTest {

    @Autowired
    private RedisCache redisCache;
    @Test
    void redisTest(){
        redisCache.deleteObject("1111");
    }

}
