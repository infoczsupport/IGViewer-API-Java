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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(value="com.infocz.infoCruise.api.mapper.rdb2", sqlSessionFactoryRef="sqlSessionFactoryRdb2")
public class MyBatisRdb2Config {
    @Value("${spring.datasource-rdb2.mapper-locations}") private String mybatisPath;
	@Value("${spring.datasource-rdb2.mybatis-config}")   private String configPath;

    @Bean
    @Qualifier("dataSourceRdb2")
    @ConfigurationProperties(prefix = "spring.datasource-rdb2")
    public DataSource dataSourceRdb2() {    
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    } 
    
    @Bean(name = "sqlSessionFactoryRdb2")
    public SqlSessionFactory sqlSessionFactoryRdb2(@Qualifier("dataSourceRdb2") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mybatisPath));
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource(configPath);
        sqlSessionFactoryBean.setConfigLocation(myBatisConfig);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sessionTemplateRdb2")
    public SqlSessionTemplate sessionTemplateRdb2(@Qualifier("sqlSessionFactoryRdb2") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}