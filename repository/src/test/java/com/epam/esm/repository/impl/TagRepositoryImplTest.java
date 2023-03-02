package com.epam.esm.repository.impl;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.RepositoryTestDataFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * tag repository tests
 */

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class TagRepositoryImplTest {
    @Mock
    private final TagRepository mockTagRepository;

    private final TagRepository tagRepository;

    @Autowired
    public TagRepositoryImplTest(TagRepository tagRepository) {
        mockTagRepository = mock(TagRepositoryImpl.class);
        this.tagRepository = tagRepository;
    }

    @Test
    @Order(1)
    void shouldReturnCorrectListOfTagsTest() {
        assertEquals(5, tagRepository.findAll().size());
    }

    @Test
    @Order(2)
    void shouldThrowDataAccessExceptionOnFindAllTest() {
        doThrow(new DataAccessException("") {})
                .when(mockTagRepository)
                        .findAll();
        assertThrows(DataAccessException.class,
                mockTagRepository::findAll);
    }


    @Test
    @Order(3)
    void shouldReturnCorrectOptionalIfTagWasFoundTest() {
        Optional<Tag> optionalTag = tagRepository.findById(1L);
        assertTrue(optionalTag.isPresent());
        Tag tag = optionalTag.get();

        assertEquals(1L, tag.getId());
        assertEquals("name1", tag.getName());
    }

    @Test
    @Order(4)
    void shouldThrowEmptyResultDataAccessExceptionIfTagWasNotFoundTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(0L));
    }

    @Test
    @Order(5)
    void shouldThrowDataAccessExceptionOnFindByIdTest() {
        doThrow(new DataAccessException("") {})
                .when(mockTagRepository)
                        .findById(anyLong());
        assertThrows(DataAccessException.class,
                () -> mockTagRepository.findById(anyLong()));
    }

    @Test
    @Order(6)
    void shouldNotThrowAnythingIfTagWasInsertedTest() {
        int size = tagRepository.findAll().size();

        Tag tag = RepositoryTestDataFactory.createTag("some name");
        Long id = tagRepository.insert(tag);

        Optional<Tag> optionalTag =
                tagRepository.findById(id);
        assertTrue(optionalTag.isPresent());
        assertEquals(++size, tagRepository.findAll().size());
    }

    @Test
    @Order(7)
    void shouldThrowDataAccessExceptionIfNullObjectIsInsertedTest() {
        Tag nullTag = RepositoryTestDataFactory.createNullTag();

        assertThrows(DataAccessException.class,
                () -> tagRepository.insert(nullTag));
    }

    @Test
    @Order(8)
    void shouldThrowDataAccessExceptionOnInsertTest() {
        Tag tag = RepositoryTestDataFactory.createTag("some name");

        doThrow(new DataAccessException("") {})
                .when(mockTagRepository)
                        .insert(tag);
        assertThrows(DataAccessException.class,
                () -> mockTagRepository.insert(tag));
    }

    @Test
    @Order(9)
    void shouldNotThrowAnythingIfTagWasDeletedTest() {
        int size = tagRepository.findAll().size();
        tagRepository.delete(1L);

        assertEquals(--size, tagRepository.findAll().size());
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(1L));
    }

    @Test
    @Order(10)
    void shouldThrowDataAccessExceptionOnDeleteTest() {
        doThrow(new DataAccessException("") {})
                .when(mockTagRepository)
                        .delete(anyLong());
        assertThrows(DataAccessException.class,
                () -> mockTagRepository.delete(anyLong()));
    }
}