package com.epam.esm.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * configuration for repository classes
 */

@Configuration
@Profile("prod")
@RequiredArgsConstructor
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
public class RepositoryConfig {
    @Value("${db.pool.size}")
    public String POOL_SIZE;
    @Value("${db.driver}")
    public String DRIVER ;
    @Value("${db.username}")
    public String USERNAME;
    @Value("${db.password}")
    public String PASSWORD;
    @Value("${db.url}")
    public String URL;

    /**
     * bean for interacting with database
     *
     * @return instance of DataSource for given datasource properties
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setInitialSize(Integer.parseInt(POOL_SIZE));
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    /**
     * bean for handling transactional methods in service layer
     *
     * @param dataSource DataSource
     * @return instance of DataSourceTransactionManager for given datasource
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * bean for field jdbcTemplate in CertificateRepositoryImpl and TagRepositoryImpl classes
     *
     * @param dataSource DataSource
     * @return instance of JdbcTemplate for given datasource
     * @see com.epam.esm.repository.impl.TagRepositoryImpl#jdbcTemplate
     * @see com.epam.esm.repository.impl.CertificateRepositoryImpl#jdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
