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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by superman on 2019/2/14.
 */

@Configuration
@MapperScan(basePackages = "com.wh.web.myweb.dao.mapper.one", sqlSessionTemplateRef = "SqlSessionTemplateOne")
public class DataSourceOneConfiguration {

    @Bean(name = "DataSourceOne")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.one")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "SqlSessionFactoryOne")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("DataSourceOne") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/one/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "TransactionManagerOne")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("DataSourceOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionTemplateOne")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactoryOne") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "jdbcTemplateOne")
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier("DataSourceOne") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
