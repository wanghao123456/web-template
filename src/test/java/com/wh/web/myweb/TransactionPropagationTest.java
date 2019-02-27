package com.wh.web.myweb;

import com.wh.web.myweb.bo.PhotoBO;
import com.wh.web.myweb.dao.mapper.one.PhotoMapperOne;
import com.wh.web.myweb.dao.mapper.two.PhotoMapperTwo;
import com.wh.web.myweb.service.PhotoService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 启动参数加上  -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true -Dcglib.debugLocation=D:\cglib  以便查看动态代理生成的字节码文件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionPropagationTest {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoMapperOne photoMapperOne;

    @Autowired
    private PhotoMapperTwo photoMapperTwo;

    private static PhotoBO photoBO = new PhotoBO();

    /**
     * 测试开始之前初始化需要的数据
     */
    @BeforeClass
    public static void testBeforeClass() {
        photoBO.setName("transaction");
        photoBO.setUrl("xxx/xxx/transaction");
    }

    /**
     * 每个测试方法运行之前都清空两个数据库中对应的表中的数据
     */
    @Before
    public void testBefore() {
        photoMapperOne.delete(null);
        photoMapperTwo.delete(null);
    }

    @Test(expected = RuntimeException.class)
    public void test() {
        photoService.base(photoBO);
    }
}
