package com.epam.esm.controller;

import com.epam.esm.config.ControllerTestConfig;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.reponse.ResponseData;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.impl.CertificateServiceImpl;
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

import static com.epam.esm.util.ControllerTestEntityHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ControllerTestConfig.class})
@WebAppConfiguration
public class CertificateControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private CertificateService certificateService;
    @InjectMocks
    private CertificateController certificateController;

    @BeforeEach
    public void setUp() {
        certificateService = mock(CertificateServiceImpl.class);
        certificateController = new CertificateController(certificateService);
    }

    @Nested
    class GetAllTest {
        @Test
        public void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundTest()
                throws NotFoundException {
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .findAll();
            assertThrows(NotFoundException.class,
                    () -> certificateController.getAll());
        }

        @Test
        public void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownTest()
                throws NotFoundException {
            when(certificateService.findAll())
                    .thenReturn(List.of(certificate));
            assertEquals(new ResponseData<>(List.of(certificate)),
                    certificateController.getAll());
        }
    }

    @Nested
    class GetByIdTest {
        @Test
        public void shouldThrowNotFoundExceptionIfCertificateWasNotFoundTest()
                throws NotFoundException {
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> certificateController.getById(0L));
        }

        @Test
        public void shouldReturnResponseWithCorrectCertificateIfNoExceptionWasThrownTest()
                throws NotFoundException {
            when(certificateService.findById(0L))
                    .thenReturn(certificate);
            assertEquals(new ResponseData<>(certificate),
                    certificateController.getById(0L));
        }
    }

    @Nested
    class GetByTagTest {

        @BeforeEach
        public void setUp() {
            bindingResult = mock(BindingResult.class);
        }

        @Test
        public void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentTest() {
            when(bindingResult.hasErrors())
                    .thenReturn(true);
            assertThrows(InvalidRequestBodyException.class,
                    () -> certificateController.getByTag(tag, bindingResult));
        }
        @Test
        public void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundTest()
                throws NotFoundException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .findByTag(tag);
            assertThrows(NotFoundException.class,
                    () -> certificateController.getByTag(tag, bindingResult));
        }

        @Test
        public void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownTest()
                throws NotFoundException, InvalidRequestBodyException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            when(certificateService.findByTag(tag))
                    .thenReturn(List.of(certificate));
            assertEquals(new ResponseData<>(List.of(certificate)),
                    certificateController.getByTag(tag, bindingResult));
        }
    }

    @Nested
    class GetBySearchFilterTest {
        @BeforeEach
        public void setUp() {
            bindingResult = mock(BindingResult.class);
        }

        @Test
        public void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentTest() {
            when(bindingResult.hasErrors())
                    .thenReturn(true);
            assertThrows(InvalidRequestBodyException.class,
                    () -> certificateController.getBySearchFilter(invalidSearchFilter, bindingResult));
        }
        @Test
        public void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundTest()
                throws NotFoundException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .findBySearchFilter(invalidSearchFilter);
            assertThrows(NotFoundException.class,
                    () -> certificateController.getBySearchFilter(invalidSearchFilter, bindingResult));
        }

        @Test
        public  void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownTest()
                throws NotFoundException, InvalidRequestBodyException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            when(certificateService.findBySearchFilter(invalidSearchFilter))
                    .thenReturn(List.of(certificate));
            assertEquals(new ResponseData<>(List.of(certificate)),
                    certificateController.getBySearchFilter(invalidSearchFilter, bindingResult));
        }
    }

    @Nested
    class GetBySortFilterTest {
        @BeforeEach
        public void setUp() {
            bindingResult = mock(BindingResult.class);
        }

        @Test
        public void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentTest() {
            when(bindingResult.hasErrors())
                    .thenReturn(true);
            assertThrows(InvalidRequestBodyException.class,
                    () -> certificateController.getBySortFilter(invalidSortFilter, bindingResult));
        }
        @Test
        public void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundTest()
                throws NotFoundException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .findBySortFilter(invalidSortFilter);
            assertThrows(NotFoundException.class,
                    () -> certificateController.getBySortFilter(invalidSortFilter, bindingResult));
        }

        @Test
        public void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownTest()
                throws NotFoundException, InvalidRequestBodyException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            when(certificateService.findBySortFilter(invalidSortFilter))
                    .thenReturn(List.of(certificate));
            assertEquals(new ResponseData<>(List.of(certificate)),
                    certificateController.getBySortFilter(invalidSortFilter, bindingResult));
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
                    () -> certificateController.create(certificate, bindingResult));
        }
        @Test
        public void shouldThrowModificationExceptionIfExceptionWasThrownTest()
                throws ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(ModificationException.class)
                    .when(certificateService)
                            .create(certificate);
            assertThrows(ModificationException.class,
                    () -> certificateController.create(certificate, bindingResult));
        }

        @Test
        public void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownTest()
                throws InvalidRequestBodyException, ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doNothing()
                    .when(certificateService)
                            .create(certificate);
            assertEquals(HttpStatus.OK,
                    certificateController.create(certificate, bindingResult).getStatus());
        }
    }

    @Nested
    class EditTest {
        @BeforeEach
        public void setUp() {
            bindingResult = mock(BindingResult.class);
        }

        @Test
        public void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentTest() {
            when(bindingResult.hasErrors())
                    .thenReturn(true);
            assertThrows(InvalidRequestBodyException.class,
                    () -> certificateController.edit(certificate, bindingResult));
        }

        @Test
        public void shouldThrowNotFoundExceptionIfCertificateWasNotFoundTest()
                throws NotFoundException, ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .update(certificate);
            assertThrows(NotFoundException.class,
                    () -> certificateController.edit(certificate, bindingResult));
        }

        @Test
        public void shouldThrowModificationExceptionIfExceptionWasThrownTest()
                throws NotFoundException, ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            doThrow(ModificationException.class)
                    .when(certificateService)
                            .update(certificate);
            assertThrows(ModificationException.class,
                    () -> certificateController.edit(certificate, bindingResult));
        }

        @Test
        public void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownTest()
                throws InvalidRequestBodyException, NotFoundException, ModificationException {
            when(bindingResult.hasErrors())
                    .thenReturn(false);
            assertEquals(HttpStatus.OK,
                    certificateController.edit(certificate, bindingResult).getStatus());
        }
    }

    @Nested
    class DeleteByIdTest {
        @Test
        public void shouldThrowNotFoundExceptionIfCertificateWasNotFoundTest()
                throws NotFoundException, ModificationException {
            doThrow(NotFoundException.class)
                    .when(certificateService)
                            .delete(0L);
            assertThrows(NotFoundException.class,
                    () -> certificateController.deleteById(0L));
        }

        @Test
        public void shouldThrowModificationExceptionIfExceptionWasThrownTest()
                throws NotFoundException, ModificationException {
            doThrow(ModificationException.class)
                    .when(certificateService)
                            .delete(0L);
            assertThrows(ModificationException.class,
                    () -> certificateController.deleteById(0L));
        }

        @Test
        public void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownTest()
                throws NotFoundException, ModificationException {
            doNothing()
                    .when(certificateService)
                            .delete(0L);
            assertEquals(HttpStatus.OK,
                    certificateController.deleteById(0L).getStatus());
        }
    }
}
