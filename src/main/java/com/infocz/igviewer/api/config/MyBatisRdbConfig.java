package com.infocz.igviewer.api.config;

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
@MapperScan(value="com.infocz.igviewer.api.mapper.rdb", sqlSessionFactoryRef="sqlSessionFactoryRdb")
public class MyBatisRdbConfig {
    @Value("${spring.datasource-rdb.mapper-locations}") private String mybatisPath;
	@Value("${spring.datasource-rdb.mybatis-config}")   private String configPath;

    @Bean
    @Qualifier("dataSourceRdb")
    @ConfigurationProperties(prefix = "spring.datasource-rdb")
    public DataSource dataSourceRdb() {    
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    } 
    
    @Bean(name = "sqlSessionFactoryRdb")
    public SqlSessionFactory sqlSessionFactoryRdb(@Qualifier("dataSourceRdb") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mybatisPath));
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource(configPath);
        sqlSessionFactoryBean.setConfigLocation(myBatisConfig);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sessionTemplateRdb")
    public SqlSessionTemplate sessionTemplateRdb(@Qualifier("sqlSessionFactoryRdb") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}