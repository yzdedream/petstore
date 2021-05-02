package com.kelvin.petstore.config;

import org.dalesbred.Database;
import org.dalesbred.dialect.Dialect;
import org.dalesbred.dialect.PostgreSQLDialect;
import org.dalesbred.integration.spring.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public DatabaseConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Database dalesbredDatabase() {
        Dialect dialect = new PostgreSQLDialect();
        SpringTransactionManager springTransactionManager
                = new SpringTransactionManager(
                this.dataSource, transactionManager);

        Database db = new Database(springTransactionManager, dialect);
        db.setAllowImplicitTransactions(false);
        return db;
    }
}
