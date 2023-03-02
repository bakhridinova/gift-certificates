package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.util.mapper.CertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static com.epam.esm.util.DataBaseQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;
    private final CertificateRowMapper certificateRowMapper;

    @Override
    public List<Certificate> findAll() {
        log.info("********** querying all rows from certificate table...");
        List<Certificate> certificates = jdbcTemplate.query(
                    FIND_ALL_CERTIFICATES.getQuery(), certificateRowMapper);
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        log.info("********** querying a row from certificate table by id...");
        Certificate certificate = jdbcTemplate.queryForObject(
                FIND_CERTIFICATE_BY_ID.getQuery(),
                        certificateRowMapper, id);
        if (certificate == null) {
            return Optional.empty();
        }

        setAllTags(certificate);
        return Optional.of(certificate);
    }

    @Override
    public List<Certificate> findByTag(Tag tag) {
        log.info("********** querying rows from certificate table by tag...");
        List<Certificate> certificates = jdbcTemplate.query(
                FIND_CERTIFICATES_BY_TAG.getQuery(),
                        certificateRowMapper, tag.getName());
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public List<Certificate> findBySearchFilter(SearchFilter searchFilter) {
        log.info("********** querying rows from certificate table by search filter...");
        String searchType = searchFilter.getSearchType().getStringValue();
        String searchValue = searchFilter.getSearchPlace()
                .getStringValue(searchFilter.getSearchValue());
        List<Certificate> certificates = jdbcTemplate.query(
                String.format(FIND_CERTIFICATES_BY_TYPE.getQuery(),
                        searchType, searchValue), certificateRowMapper);
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public List<Certificate> findBySortFilter(SortFilter sortFilter) {
        log.info("********** querying rows from certificate table by sort filter...");
        String sortType = sortFilter.getSortType().getStringValue();
        String sortOrder = sortFilter.getSortOrder().getStringValue();
        List<Certificate> certificates = jdbcTemplate.query(
                String.format(FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE.getQuery(),
                                sortType, sortOrder), certificateRowMapper);
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public Long insert(Certificate certificate) {
        log.info("********** adding new row into certificate table...");
        return jdbcTemplate.queryForObject(
                INSERT_CERTIFICATE.getQuery(), Long.class,
                certificate.getName(), certificate.getDescription(),
                        certificate.getPrice(), certificate.getDuration());
    }

    @Override
    public void update(Certificate certificate) {
        log.info("********** updating row in certificate table...");
        jdbcTemplate.update(UPDATE_CERTIFICATE.getQuery(),
                certificate.getName(), certificate.getDescription(),
                        certificate.getPrice(), certificate.getDuration(), certificate.getId());
        jdbcTemplate.update(DELETE_ALL_CERTIFICATE_TAGS.getQuery(), certificate.getId());
    }

    @Override
    public void insertTags(Certificate certificate) {
        log.info("********** adding new rows into tag table...");
        for (Tag tag1 : certificate.getTags()) {
            Long id = jdbcTemplate.queryForObject(
                    INSERT_TAG.getQuery(), Long.class, tag1.getName());
            tag1.setId(id);
        }

        log.info("********** adding new rows into certificate_tag table...");
        certificate.getTags().forEach(tag ->
                jdbcTemplate.update(INSERT_TAG_TO_CERTIFICATE.getQuery(), certificate.getId(), tag.getId()));
    }

    @Override
    public void delete(Long id) {
        log.info("********** deleting row from certificate table...");
        jdbcTemplate.update(DELETE_CERTIFICATE.getQuery(), id);
    }

    private void setAllTags(Certificate certificate) {
        certificate.setTags(new TreeSet<>(jdbcTemplate.query(
                FIND_ALL_CERTIFICATE_TAGS.getQuery(), tagRowMapper, certificate.getId())));
    }
}
