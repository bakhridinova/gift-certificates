package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ServiceTestDataFactory;
import com.epam.esm.util.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * tag service tests
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class TagServiceImplTest {
    @Mock
    private TagMapper tagMapper;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        tagMapper = mock(TagMapper.class);
        tagRepository = mock(TagRepositoryImpl.class);
        tagService = new TagServiceImpl(tagRepository, tagMapper);
    }

    @Test
    public void shouldThrowNotFoundExceptionIfDataAccessExceptionWasThrownOnFindAllTest() {
        doThrow(new DataAccessException("") {})
                .when(tagRepository)
                .findAll();
        assertThrows(NotFoundException.class,
                () -> tagService.findAll());
    }

    @Test
    public void shouldReturnCorrectListOfTagsIfNoExceptionWasThrownOnFindAllTest() throws NotFoundException {
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagRepository.findAll())
                .thenReturn(List.of(tag));
        when(tagMapper.toTagDTO(tag))
                .thenReturn(tagDTO);
        assertEquals(List.of(tagDTO),
                tagService.findAll());
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnFindByIdTest() {
        doThrow(EmptyResultDataAccessException.class)
                .when(tagRepository)
                        .findById(0L);
        assertThrows(NotFoundException.class,
                () -> tagService.findById(0L));
    }

    @Test
    public void shouldReturnCorrectTagIfTagWithSuchIdWasOnFindByIdFound() throws NotFoundException {
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagRepository.findById(0L))
                .thenReturn(Optional.of(tag));
        when(tagMapper.toTagDTO(tag))
                .thenReturn(tagDTO);
        assertEquals(tagDTO,
                tagService.findById(0L));
    }

    @Test
    public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownOnCreateTest() {
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagMapper.toTag(tagDTO))
                .thenReturn(tag);
        doThrow(new DataAccessException("") {})
                .when(tagRepository)
                        .insert(tag);
        assertThrows(ModificationException.class,
                () -> tagService.create(tagDTO));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownOnCreateTest() {
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagMapper.toTag(tagDTO))
                .thenReturn(tag);
        when(tagRepository.insert(tag))
                .thenReturn(0L);
        assertDoesNotThrow(() ->
                tagService.create(tagDTO));
    }

    @Test
    public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownOnDeleteTest() {
        Tag tag =
                ServiceTestDataFactory.createTag();

        when(tagRepository.findById(0L))
                .thenReturn(Optional.of(tag));
        doThrow(new DataAccessException("") {})
                .when(tagRepository)
                        .delete(0L);
        assertThrows(ModificationException.class,
                () -> tagService.delete(0L));
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnDeleteTest() {
        doThrow(EmptyResultDataAccessException.class)
                .when(tagRepository)
                        .findById(0L);
        assertThrows(NotFoundException.class,
                () -> tagService.delete(0L));
    }
    @Test
    public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownOnDeleteTest() {
        Tag tag = ServiceTestDataFactory.createTag();

        when(tagRepository.findById(0L))
                .thenReturn(Optional.of(tag));
        doNothing()
                .when(tagRepository)
                        .delete(0L);
        assertDoesNotThrow(() ->
                tagService.delete(0L));
    }
}
