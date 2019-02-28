package com.wh.web.myweb;

import com.wh.web.myweb.bo.PhotoBO;
import com.wh.web.myweb.service.PhotoService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Qualifier("jdbcTemplateOne")
    private JdbcTemplate jdbcTemplateOne;

    @Autowired
    @Qualifier("jdbcTemplateTwo")
    private JdbcTemplate jdbcTemplateTwo;

    private static PhotoBO photoBO = new PhotoBO();

    private String createTableSql = "" +
            "create table photo\n" +
            "(\n" +
            "  id bigint auto_increment,\n" +
            "  name varchar(64) default '' not null,\n" +
            "  url varchar(128) default '' not null\n" +
            ")";

    /**
     * 此处注意下 H2的truncate语句和MySQL的truncate语句有细微差别（H2 多了 table）
     */
    private String truncateTableSql = "truncate table photo";

    private boolean init = true;

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
        if (init) {
            jdbcTemplateOne.execute(createTableSql);
            jdbcTemplateTwo.execute(createTableSql);
            init = false;
        }
        jdbcTemplateOne.execute(truncateTableSql);
        jdbcTemplateTwo.execute(truncateTableSql);
    }

    @Test(expected = RuntimeException.class)
    public void test() {
        photoService.base(photoBO);
    }
}
