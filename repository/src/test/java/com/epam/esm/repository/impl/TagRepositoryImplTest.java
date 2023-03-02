package com.epam.esm.repository.impl;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.RepositoryTestEntityHolder.tag;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tag repository tests
 */

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class TagRepositoryImplTest extends AbstractIntegrationTest {
    private final TagRepository tagRepository;

    @Autowired
    public TagRepositoryImplTest(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Test
    public void shouldReturnCorrectListOfTagsTest() {
        List<Tag> tags = tagRepository.findAll();
        assertNotNull(tags);
        assertEquals(4, tags.size());
    }

    @Test
    public void shouldReturnCorrectOptionalIfTagWasFoundTest() {
        Optional<Tag> optionalTag = tagRepository.findById(1L);
        assertNotNull(optionalTag);
        assertTrue(optionalTag.isPresent());
        assertEquals(1L, optionalTag.get().getId());
        assertEquals("name", optionalTag.get().getName());
    }

    @Test
    public void shouldThrowEmptyResultDataAccessExceptionIfTagWasNotFoundTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(0L));
    }

    @Test
    public void shouldNotThrowAnythingIfTagWasInsertedTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(5L));
        int size = tagRepository.findAll().size();

        Long id = tagRepository.insert(tag);
        Optional<Tag> optionalTag = tagRepository.findById(id);
        assertNotNull(optionalTag);
        assertTrue(optionalTag.isPresent());
        assertEquals(++size, tagRepository.findAll().size());
    }

    @Test
    public void shouldThrowDataAccessExceptionIfTagNameIsNullTest() {
        int size = tagRepository.findAll().size();
        assertThrows(DataAccessException.class,
                () -> tagRepository.insert(Tag.builder().build()));
        assertEquals(size, tagRepository.findAll().size());
    }

    @Test
    public void shouldThrowDataAccessExceptionIfTagNameIsNotUniqueTest() {
        int size = tagRepository.findAll().size();
        assertThrows(DataAccessException.class,
                () -> tagRepository.insert(tag));
        assertEquals(size, tagRepository.findAll().size());
    }

    @Test
    public void shouldNotThrowAnythingIfTagWasDeletedTest() {
        assertTrue(tagRepository.findById(1L).isPresent());
        int size = tagRepository.findAll().size();

        tagRepository.delete(1L);
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(1L));
        assertEquals(--size, tagRepository.findAll().size());
    }
}