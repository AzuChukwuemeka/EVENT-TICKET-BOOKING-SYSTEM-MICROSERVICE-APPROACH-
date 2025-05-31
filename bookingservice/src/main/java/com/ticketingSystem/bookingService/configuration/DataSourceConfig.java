package com.ticketingSystem.bookingService.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(dotenv.get("DB_URL"));
//        hikariConfig.setUsername(dotenv.get("DB_USERNAME"));
//        hikariConfig.setPassword(dotenv.get("DB_PASSWORD"));
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
    };
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
