package com.wh.web.myweb;

import com.wh.web.myweb.bo.PhotoBO;
import com.wh.web.myweb.service.impl.PhotoServiceImpl;
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
    private PhotoServiceImpl photoService;

    @Test
    public void test() {
        PhotoBO photoBO = new PhotoBO();
        photoBO.setName("transaction");
        photoBO.setUrl("xxx/xxx/transaction");
        photoService.base(photoBO);
    }
}
