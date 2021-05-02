package com.kelvin.petstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    private final Environment env;

    @Autowired
    public DataSourceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        String jdbcUrl = "jdbc:postgresql://"
                + env.getRequiredProperty("db.hostname")
                + ":" + env.getRequiredProperty("db.port") + "/"
                + env.getRequiredProperty("db.dbname");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password", ""));
        return dataSource;
    }
}
