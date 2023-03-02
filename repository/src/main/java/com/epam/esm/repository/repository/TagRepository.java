package com.epam.esm.repository.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository for manipulating Tag data in table tag
 */

public interface TagRepository extends BaseRepository<Tag> {
    /**
     * retrieve all tag data
     *
     * @return List of all tags
     */
    @Override
    List<Tag> findAll();

    /**
     * retrieves tag data according to id
     *
     * @param id Long tag id
     * @return Optional.of(Tag) if tag with such id exists, otherwise Optional.empty()
     */
    @Override
    Optional<Tag> findById(Long id);

    /**
     * insertTags new tag
     *
     * @param tag Tag
     * @return Long id of inserted tag
     */
    @Override
    Long insert(Tag tag);

    /**
     * unsupported operation
     */
    @Override
    void update(Tag tag);

    /**
     * delete tag according to id
     *
     * @param id Long tag id
     */
    @Override
    void delete(Long id);
}
