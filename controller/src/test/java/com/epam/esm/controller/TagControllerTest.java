package com.epam.esm.controller;

import com.epam.esm.config.ControllerTestConfig;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.reponse.ResponseData;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

import static com.epam.esm.util.ControllerTestEntityHolder.tag;
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
        tagController = new TagController(tagService);
    }

    @Nested
    class FindAllTest {
        @Test
        public void shouldThrowNotFoundExceptionIfTagsWereNotFoundTest()
                throws NotFoundException {
            doThrow(NotFoundException.class)
                    .when(tagService)
                            .findAll();
            assertThrows(NotFoundException.class,
                    () -> tagService.findAll());
        }

        @Test
        public void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownTest()
                throws NotFoundException {
            when(tagService.findAll())
                    .thenReturn(List.of(tag));
            assertEquals(new ResponseData<>(List.of(tag)),
                    tagController.getAll());
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        public void shouldThrowNotFoundExceptionIfTagWasNotFoundTest()
                throws NotFoundException {
            doThrow(NotFoundException.class)
                    .when(tagService)
                            .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> tagController.getById(0L));
        }

        @Test
        public void shouldReturnResponseWithCorrectTagIfNoExceptionWasThrownTest()
                throws NotFoundException {
            when(tagService.findById(0L))
                    .thenReturn(tag);
            assertEquals(new ResponseData<>(tag),
                    tagController.getById(0L));
        }
    }

    @Nested
    class CreateTest {
        @BeforeEach
        public void setUp() {
            bindingResult = mock(BindingResult.class);
        }

        @Test
        public void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentTest() {
            when(bindingResult.hasErrors())
                    .thenReturn(true);
            assertThrows(InvalidRequestBodyException.class,
                    () -> tagController.create(tag, bindingResult));
        }
        @Test
        public void shouldThrowModificationExceptionIfExceptionWasThrownTest()
                throws ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(ModificationException.class)
                    .when(tagService)
                            .create(tag);
            assertThrows(ModificationException.class,
                    () -> tagController.create(tag, bindingResult));
        }

        @Test
        public void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownTest()
                throws InvalidRequestBodyException, ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doNothing()
                    .when(tagService)
                            .create(tag);
            assertEquals(HttpStatus.OK,
                    tagController.create(tag, bindingResult).getStatus());
        }
    }

    @Nested
    class DeleteTest {
        @Test
        public void shouldThrowNotFoundExceptionIfTagWasNotFoundTest()
                throws NotFoundException, ModificationException {
            doThrow(NotFoundException.class)
                    .when(tagService)
                            .delete(0L);
            assertThrows(NotFoundException.class,
                    () -> tagController.deleteById(0L));
        }

        @Test
        public void shouldThrowModificationExceptionIfExceptionWasThrownTest()
                throws NotFoundException, ModificationException {
            doThrow(NotFoundException.class)
                    .when(tagService)
                            .delete(0L);
            assertThrows(NotFoundException.class,
                    () -> tagController.deleteById(0L));
        }

        @Test
        public void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownTest()
                throws NotFoundException, ModificationException {
            doNothing()
                    .when(tagService)
                            .delete(0L);
            assertEquals(HttpStatus.OK,
                    tagController.deleteById(0L).getStatus());
        }
    }
}