package org.example.reactiveprograming.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class MyBatisConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        return new DriverManagerDataSource(
                Objects.requireNonNull(env.getProperty("spring.datasource.url")),
                Objects.requireNonNull(env.getProperty("spring.datasource.username")) ,
                Objects.requireNonNull(env.getProperty("spring.datasource.password")));
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory.getObject();
    }
}