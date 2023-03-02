package com.epam.esm.config;

import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.mapper.CertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

/**
 * configuration for repository test classes
 */

@Configuration
@Testcontainers
public class RepositoryTestConfig {
    @Container
    private static PostgreSQLContainer<?> container;

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        container = new PostgreSQLContainer<>("postgres:15");
        container.withInitScript("data.sql");
        container.start();
        return container;
    }

    /**
     * bean for interacting with test container
     *
     * @return instance of DataSource for given container properties
     */
    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> container) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        dataSource.setDriverClassName(container.getDriverClassName());
        return dataSource;
    }

    /**
     * bean for handling transactional test methods in test classes
     *
     * @param dataSource DataSource
     * @return instance of DataSourceTransactionManager for given datasource
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
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

    /**
     * bean for mapping rows of tag result set into tag
     *
     * @return instance of TagRowMapper
     * @see TagRowMapper#mapRow(ResultSet, int)
     */
    @Bean
    public TagRowMapper tagRowMapper() {
        return new TagRowMapper();
    }

    /**
     * bean for mapping rows of certificate result set into certificate
     *
     * @return instance of CertificateRowMapper
     * @see CertificateRowMapper#mapRow(ResultSet, int)
     */
    @Bean
    public CertificateRowMapper certificateRowMapper() {
        return new CertificateRowMapper();
    }

    /**
     * bean for field tagRepository in TagRepositoryImplTest class
     *
     * @param jdbcTemplate JdbcTemplate
     * @param tagRowMapper TagRowMapper
     * @return TagRepository instance of TagRepositoryImpl
     * @see com.epam.esm.repository.impl.TagRepositoryImplTest#tagRepository
     */
    @Bean
    @Scope("prototype")
    public TagRepository tagRepository(JdbcTemplate jdbcTemplate,
                                       TagRowMapper tagRowMapper) {
        return new TagRepositoryImpl(
                jdbcTemplate,
                tagRowMapper);
    }

    /**
     * bean for field certificateRepository in CertificateRepositoryImplTest class
     *
     * @param jdbcTemplate JdbcTemplate
     * @param tagRowMapper TagRowMapper
     * @param certificateRowMapper CertificateRowMapper
     * @return CertificateRepository instance of CertificateRepositoryImpl
     * @see com.epam.esm.repository.impl.CertificateRepositoryImplTest#certificateRepository
     */
    @Bean
    public CertificateRepository certificateRepository(JdbcTemplate jdbcTemplate,
                                                       TagRowMapper tagRowMapper,
                                                       CertificateRowMapper certificateRowMapper) {
        return new CertificateRepositoryImpl(
                jdbcTemplate,
                tagRowMapper,
                certificateRowMapper);
    }
}
