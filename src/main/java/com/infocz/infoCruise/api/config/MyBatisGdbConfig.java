package com.infocz.infoCruise.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(value="com.infocz.infoCruise.api.mapper.gdb", sqlSessionFactoryRef="sqlSessionFactoryGdb")
public class MyBatisGdbConfig {
    @Value("${spring.datasource-gdb.mapper-locations}") String mybatisPath;
	@Value("${spring.datasource-gdb.mybatis-config}")   private String configPath;

    @Primary
    @Bean(name = "dataSourceGdb")
    @ConfigurationProperties(prefix = "spring.datasource-gdb")
    public DataSource dataSourceGdb() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "sqlSessionFactoryGdb")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceGdb") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mybatisPath));
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource(configPath);
        sqlSessionFactoryBean.setConfigLocation(myBatisConfig);
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionTemplateGdb")
    public SqlSessionTemplate sqlSessionTemplateGdb(@Qualifier("sqlSessionFactoryGdb") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "jdbcTemplateGdb")
    public JdbcTemplate jdbcTemplateGdb(@Qualifier("dataSourceGdb") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}