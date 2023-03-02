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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.ResultSet;

/**
 * configuration for repository test classes
 */

@Configuration
public class RepositoryTestConfig {
    /**
     * bean for interacting with test container
     *
     * @return instance of DataSource for given data scripts
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(System.getProperty("db.url"));
        dataSource.setUsername(System.getProperty("db.username"));
        dataSource.setPassword(System.getProperty("db.password"));
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
