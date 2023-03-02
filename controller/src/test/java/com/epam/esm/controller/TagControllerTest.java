package com.epam.esm.controller;

import com.epam.esm.config.ControllerTestConfig;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.reponse.ResponseData;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.ControllerTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ControllerTestConfig.class})
@WebAppConfiguration
class TagControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private TagService tagService;
    @InjectMocks
    private TagController tagController;

    @BeforeEach
    public void setUp() {
        tagService = mock(TagServiceImpl.class);
        bindingResult = mock(BindingResult.class);
        tagController = new TagController(tagService);
    }

    @Test
    void shouldThrowNotFoundExceptionIfTagsWereNotFoundOnGetAllTest()
            throws NotFoundException {
        doThrow(NotFoundException.class)
                .when(tagService)
                        .findAll();
        assertThrows(NotFoundException.class,
                () -> tagService.findAll());
    }

    @Test
    void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownOnGetAllTest()
            throws NotFoundException {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(tagService.findAll())
                .thenReturn(List.of(tagDTO));
        assertEquals(new ResponseData<>(List.of(tagDTO), HttpStatus.OK),
                tagController.getAll());
    }

    @Test
    void shouldThrowNotFoundExceptionIfTagWasNotFoundOnGetByIdTest()
            throws NotFoundException {
        doThrow(NotFoundException.class)
                .when(tagService)
                        .findById(0L);
        assertThrows(NotFoundException.class,
                () -> tagController.getById(0L));
    }

    @Test
    void shouldReturnResponseWithCorrectTagIfNoExceptionWasThrownOnGetByIdTest()
            throws NotFoundException {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(tagService.findById(0L))
                .thenReturn(tagDTO);
        assertEquals(new ResponseData<>(tagDTO, HttpStatus.OK),
                tagController.getById(0L));
    }


    @Test
    void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentOnCreateTest() {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(bindingResult.hasErrors())
                .thenReturn(true);
        assertThrows(InvalidRequestBodyException.class,
                () -> tagController.create(tagDTO, bindingResult));
    }
    @Test
    void shouldThrowModificationExceptionIfExceptionWasThrownOnCreateTest()
            throws ModificationException {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doThrow(ModificationException.class)
                .when(tagService)
                        .create(tagDTO);
        assertThrows(ModificationException.class,
                () -> tagController.create(tagDTO, bindingResult));
    }

    @Test
    void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownOnCreateTest()
            throws InvalidRequestBodyException, ModificationException {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doNothing()
                .when(tagService)
                .create(tagDTO);
        assertEquals(HttpStatus.OK,
                tagController.create(tagDTO, bindingResult).getStatus());
    }
    @Test
    void shouldThrowNotFoundExceptionIfTagWasNotFoundOnDeleteTest()
            throws NotFoundException, ModificationException {
        doThrow(NotFoundException.class)
                .when(tagService)
                        .delete(0L);
        assertThrows(NotFoundException.class,
                () -> tagController.deleteById(0L));
    }

    @Test
    void shouldThrowModificationExceptionIfExceptionWasThrownOnDeleteTest()
            throws NotFoundException, ModificationException {
        doThrow(NotFoundException.class)
                .when(tagService)
                        .delete(0L);
        assertThrows(NotFoundException.class,
                () -> tagController.deleteById(0L));
    }

    @Test
    void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownOnDeleteTest()
            throws NotFoundException, ModificationException {
        doNothing()
                .when(tagService)
                        .delete(0L);
        assertEquals(HttpStatus.OK,
                tagController.deleteById(0L).getStatus());
    }
}