package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.mapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.DataBaseInfoAndQueryHolder.DELETE_TAG;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_ALL_TAGS;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.FIND_TAG_BY_ID;
import static com.epam.esm.util.DataBaseInfoAndQueryHolder.INSERT_TAG;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Override
    public List<Tag> findAll() {
        log.info("********** querying all rows from tag table...");
        return jdbcTemplate.query(FIND_ALL_TAGS, tagRowMapper);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        log.info("********** querying a row from tag table by id...");
        return Optional.ofNullable(jdbcTemplate
                    .queryForObject(FIND_TAG_BY_ID, tagRowMapper, id));
    }

    @Override
    public Long insert(Tag tag) {
        log.info("********** adding new row into tag table...");
        return jdbcTemplate.queryForObject(
                    INSERT_TAG,
                    Long.class,
                    tag.getName());
    }

    @Override
    public void update(Tag tag) {
    }

    @Override
    public void delete(Long id) {
        log.info("********** deleting row from tag table...");
        jdbcTemplate.update(DELETE_TAG, id);
    }
}
