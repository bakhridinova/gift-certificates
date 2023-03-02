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

import static com.epam.esm.util.DataBaseInfoAndQueryHolder.DELETE_ALL_CERTIFICATE_TAGS;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.DELETE_CERTIFICATE;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_ALL_CERTIFICATES;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_ALL_CERTIFICATE_TAGS;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_CERTIFICATES_BY_TYPE;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_CERTIFICATES_BY_TAG;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_CERTIFICATE_BY_ID;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.INSERT_CERTIFICATE;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.INSERT_TAG_TO_CERTIFICATE;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.UPDATE_CERTIFICATE;

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
                    FIND_ALL_CERTIFICATES, certificateRowMapper);
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        log.info("********** querying a row from certificate table by id...");
        Certificate certificate = jdbcTemplate.queryForObject(
                FIND_CERTIFICATE_BY_ID, certificateRowMapper, id);
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
                FIND_CERTIFICATES_BY_TAG, certificateRowMapper, tag.getName());
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public List<Certificate> findBySearchFilter(SearchFilter searchFilter) {
        log.info("********** querying rows from certificate table by search filter...");
        String searchType = searchFilter.getSearchType().getStringValue();
        String searchValue = searchFilter.getSearchPlace().getStringValue(searchFilter.getSearchValue());
        List<Certificate> certificates = jdbcTemplate.query(
                String.format(FIND_CERTIFICATES_BY_TYPE, searchType, searchValue), certificateRowMapper);
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public List<Certificate> findBySortFilter(SortFilter sortFilter) {
        log.info("********** querying rows from certificate table by sort filter...");
        String sortType = sortFilter.getSortType().getStringValue();
        String sortOrder = sortFilter.getSortOrder().getStringValue();
        List<Certificate> certificates = jdbcTemplate.query(
                String.format(FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE, sortType, sortOrder), certificateRowMapper);
        certificates.forEach(this::setAllTags);
        return certificates;
    }

    @Override
    public Long insert(Certificate certificate) {
        log.info("********** adding new row into certificate table...");
        return jdbcTemplate.queryForObject(
                INSERT_CERTIFICATE, Long.class,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration());
    }

    @Override
    public void update(Certificate certificate) {
        log.info("********** updating row in certificate table...");
        jdbcTemplate.update(
                UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getId());
        jdbcTemplate.update(
                DELETE_ALL_CERTIFICATE_TAGS,
                certificate.getId());
    }

    @Override
    public void insertTags(Certificate certificate) {
        log.info("********** adding new rows into certificate_tag table...");
        certificate.getTags().forEach(tag -> jdbcTemplate.update(
                INSERT_TAG_TO_CERTIFICATE, certificate.getId(), tag.getId()));
        setAllTags(certificate);
    }

    @Override
    public void delete(Long id) {
        log.info("********** deleting row from certificate table...");
        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    private void setAllTags(Certificate certificate) {
        certificate.setTags(new TreeSet<>(jdbcTemplate.query(
                FIND_ALL_CERTIFICATE_TAGS, tagRowMapper, certificate.getId())));
    }
}
