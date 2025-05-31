package com.ticketingSystem.seatService.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(Environment environment){
        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(environment.getProperty("DB_URL"));
//        hikariConfig.setUsername(environment.getProperty("DB_USERNAME"));
//        hikariConfig.setPassword(environment.getProperty("DB_PASSWORD"));
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:3001/ticketSystem");
        hikariConfig.setUsername("code");
        hikariConfig.setPassword("password");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setPoolName("MyHikariPool");
        hikariConfig.setLeakDetectionThreshold(15000);
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(hikariConfig);
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource datasource){
        return new JdbcTemplate(datasource);
    }
}
