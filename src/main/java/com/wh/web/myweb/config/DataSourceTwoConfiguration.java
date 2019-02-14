package com.wh.web.myweb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by superman on 2019/2/14.
 */

@Configuration
@MapperScan(basePackages = "com.wh.web.myweb.dao.mapper.two", sqlSessionTemplateRef = "SqlSessionTemplateTwo")
public class DataSourceTwoConfiguration {

    @Bean(name = "DataSourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.druid.two")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "SqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("DataSourceTwo") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/two/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "TransactionManagerTwo")
    public DataSourceTransactionManager transactionManager(@Qualifier("DataSourceTwo") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionTemplateTwo")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactoryTwo") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
