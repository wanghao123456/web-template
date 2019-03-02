package com.wh.web.myweb;

import com.wh.web.myweb.bo.PhotoBO;
import com.wh.web.myweb.dao.mapper.one.PhotoMapperOne;
import com.wh.web.myweb.dao.mapper.two.PhotoMapperTwo;
import com.wh.web.myweb.service.PhotoService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;


/**
 * 启动参数加上  -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true -Dcglib.debugLocation=D:\cglib  以便查看动态代理生成的字节码文件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder()
public class TransactionPropagationTest {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoMapperOne photoMapperOne;

    @Autowired
    private PhotoMapperTwo photoMapperTwo;

    @Autowired
    @Qualifier("jdbcTemplateOne")
    private JdbcTemplate jdbcTemplateOne;

    @Autowired
    @Qualifier("jdbcTemplateTwo")
    private JdbcTemplate jdbcTemplateTwo;

    private static PhotoBO photoBO = new PhotoBO();

    private String createTableSql = "" +
            "create table if not exists photo\n" +
            "(\n" +
            "  id bigint auto_increment,\n" +
            "  name varchar(64) default '' not null,\n" +
            "  url varchar(128) default '' not null\n" +
            ")";

    /**
     * 此处注意下 H2的truncate语句和MySQL的truncate语句有细微差别（H2 多了 table）
     */
    private String truncateTableSql = "truncate table photo";

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
        jdbcTemplateOne.execute(createTableSql);
        jdbcTemplateTwo.execute(createTableSql);
        jdbcTemplateOne.execute(truncateTableSql);
        jdbcTemplateTwo.execute(truncateTableSql);
    }

    /**
     * 接下来就开始进行单数据源，单事务管理器，多数据源，多事务管理器，以及不同数据源配合不同事务管理器的实验
     * <p>
     * 首先进行单数据源，单事务管理器情况下，Spring事务传播的特性测试
     *
     * @see org.springframework.transaction.annotation.Propagation#REQUIRED
     * <p>
     * 第一个测试 REQUIRED 机制下外方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test1() {
        try {
            photoService.baseTwo(photoBO, Propagation.REQUIRED, true, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#REQUIRED
     * <p>
     * 测试 REQUIRED 机制下内方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test2() {
        try {
            photoService.baseTwo(photoBO, Propagation.REQUIRED, false, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#REQUIRES_NEW
     * <p>
     * 测试 REQUIRES_NEW 机制下外方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test3() {
        try {
            photoService.baseTwo(photoBO, Propagation.REQUIRES_NEW, true, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#REQUIRES_NEW
     * <p>
     * 测试 REQUIRES_NEW 机制下内方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test4() {
        try {
            photoService.baseTwo(photoBO, Propagation.REQUIRES_NEW, false, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#SUPPORTS
     * <p>
     * 测试 SUPPORTS 机制下外方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test5() {
        try {
            photoService.baseTwo(photoBO, Propagation.SUPPORTS, true, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#SUPPORTS
     * <p>
     * 测试 SUPPORTS 机制下内方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test6() {
        try {
            photoService.baseTwo(photoBO, Propagation.SUPPORTS, false, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED
     * <p>
     * 测试 NOT_SUPPORTED 机制下外方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test7() {
        try {
            photoService.baseTwo(photoBO, Propagation.NOT_SUPPORTED, true, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED
     * <p>
     * 测试 NOT_SUPPORTED 机制下内方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test8() {
        try {
            photoService.baseTwo(photoBO, Propagation.NOT_SUPPORTED, false, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(2, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#NEVER
     * <p>
     * 测试 NEVER 机制下抛出异常之后事务的回滚情况
     * <p>
     * 注意当调用 NEVER 机制下的方法时有事务上下文将抛出异常
     */
    @Test()
    public void test9() {
        try {
            photoService.baseTwo(photoBO, Propagation.NEVER, false, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#NEVER
     * <p>
     * 测试 NEVER 机制下抛出异常之后事务的回滚情况
     * <p>
     * 注意当调用 NEVER 机制下的方法时有事务上下文将抛出异常
     */
    @Test()
    public void test10() {
        try {
            photoService.testNever(photoBO, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#MANDATORY
     * <p>
     * 测试 MANDATORY 机制下外方法抛出异常之后事务的回滚情况
     * <p>
     * 注意当调用 MANDATORY 机制下的方法时没有事务上下文将抛出异常
     */
    @Test()
    public void test11() {
        try {
            photoService.baseTwo(photoBO, Propagation.MANDATORY, true, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#MANDATORY
     * <p>
     * 测试 MANDATORY 机制下内方法抛出异常之后事务的回滚情况
     * <p>
     * 注意当调用 MANDATORY 机制下的方法时没有事务上下文将抛出异常
     */
    @Test()
    public void test12() {
        try {
            photoService.baseTwo(photoBO, Propagation.MANDATORY, false, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#MANDATORY
     * <p>
     * 测试 MANDATORY 机制下抛出异常之后事务的回滚情况
     * <p>
     * 注意当调用 MANDATORY 机制下的方法时没有事务上下文将抛出异常
     */
    @Test()
    public void test13() {
        try {
            photoService.testMandatory(photoBO, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#NESTED
     * <p>
     * 测试 NESTED 机制下外方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test14() {
        try {
            photoService.baseTwo(photoBO, Propagation.NESTED, true, false);
        } catch (Exception e) {
        }
        Assert.assertEquals(0, selectCountSameTable());
    }

    /**
     * @see org.springframework.transaction.annotation.Propagation#NESTED
     * <p>
     * 测试 NESTED 机制下内方法抛出异常之后事务的回滚情况
     */
    @Test()
    public void test15() {
        try {
            photoService.baseTwo(photoBO, Propagation.NESTED, false, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    /**
     * 有了上面的单数据源，单事务管理器的经验，我们再来看下多数据源，多事务管理器，不同数据源对应不同事务管理器情况下事务的机制
     * <p>
     * 首先看下多数据源，多事务管理器的情况
     *
     * @see Propagation#REQUIRED
     */
    @Test
    public void test16() {
        try {
            photoService.baseOne(photoBO, Propagation.REQUIRED, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#REQUIRED
     */
    @Test
    public void test17() {
        try {
            photoService.baseOne(photoBO, Propagation.REQUIRED, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 0};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#REQUIRES_NEW
     */
    @Test
    public void test18() {
        try {
            photoService.baseOne(photoBO, Propagation.REQUIRES_NEW, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#REQUIRES_NEW
     */
    @Test
    public void test19() {
        try {
            photoService.baseOne(photoBO, Propagation.REQUIRES_NEW, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 0};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#SUPPORTS
     */
    @Test
    public void test20() {
        try {
            photoService.baseOne(photoBO, Propagation.SUPPORTS, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#SUPPORTS
     */
    @Test
    public void test21() {
        try {
            photoService.baseOne(photoBO, Propagation.SUPPORTS, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#NOT_SUPPORTED
     */
    @Test
    public void test22() {
        try {
            photoService.baseOne(photoBO, Propagation.NOT_SUPPORTED, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#NOT_SUPPORTED
     */
    @Test
    public void test23() {
        try {
            photoService.baseOne(photoBO, Propagation.NOT_SUPPORTED, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#NEVER
     */
    @Test
    public void test24() {
        try {
            photoService.baseOne(photoBO, Propagation.NEVER, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#NEVER
     */
    @Test
    public void test25() {
        try {
            photoService.baseOne(photoBO, Propagation.NEVER, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#MANDATORY
     */
    @Test
    public void test26() {
        try {
            photoService.baseOne(photoBO, Propagation.MANDATORY, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 0};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#MANDATORY
     */
    @Test
    public void test27() {
        try {
            photoService.baseOne(photoBO, Propagation.MANDATORY, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 0};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#NESTED
     */
    @Test
    public void test28() {
        try {
            photoService.baseOne(photoBO, Propagation.NESTED, true, false);
        } catch (Exception e) {
        }
        int[] result = {0, 1};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * @see Propagation#NESTED
     */
    @Test
    public void test29() {
        try {
            photoService.baseOne(photoBO, Propagation.NESTED, false, true);
        } catch (Exception e) {
        }
        int[] result = {1, 0};
        Assert.assertArrayEquals(result, selectCountDifferentTable());
    }

    /**
     * 通过上面的测试，现在我们知道了Spring中不同的事务管理器之间不会遵循原本的事务传播机制
     * <p>
     * 下面我们再来看下不同数据源对应的不同事务管理器的情况
     * <p>
     * 通过下面的测试可以得出结论：不同数据源的事务管理器不会对当前数据源上的操作产生影响
     */
    @Test
    public void test30() {
        try {
            photoService.differentDataSourceDifferentTransactionManager(photoBO, true);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, selectCountSameTable());
    }

    private int selectCountSameTable() {
        return photoMapperTwo.selectCount(null);
    }

    private int[] selectCountDifferentTable() {
        int[] result = new int[2];
        result[0] = photoMapperOne.selectCount(null);
        result[1] = photoMapperTwo.selectCount(null);
        return result;
    }
}
