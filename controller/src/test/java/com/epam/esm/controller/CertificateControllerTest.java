package com.epam.esm.controller;

import com.epam.esm.config.ControllerTestConfig;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.reponse.ResponseData;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.impl.CertificateServiceImpl;
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
class CertificateControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private CertificateService certificateService;
    @InjectMocks
    private CertificateController certificateController;

    @BeforeEach
    public void setUp() {
        bindingResult = mock(BindingResult.class);
        certificateService = mock(CertificateServiceImpl.class);
        certificateController = new CertificateController(certificateService);
    }

    @Test
    void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundOnGetAllTest()
            throws NotFoundException {
        doThrow(NotFoundException.class)
                .when(certificateService)
                .findAll();
        assertThrows(NotFoundException.class,
                () -> certificateController.getAll());
    }

    @Test
    void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownOnGetAllTest()
            throws NotFoundException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(certificateService.findAll())
                .thenReturn(List.of(certificateDTO));
        assertEquals(new ResponseData<>(List.of(certificateDTO), HttpStatus.OK),
                certificateController.getAll());
    }

    @Test
    void shouldThrowNotFoundExceptionIfCertificateWasNotFoundOnGetByIdTest()
            throws NotFoundException {
        doThrow(NotFoundException.class)
                .when(certificateService)
                .findById(0L);
        assertThrows(NotFoundException.class,
                () -> certificateController.getById(0L));
    }
    @Test
    void shouldReturnResponseWithCorrectCertificateIfNoExceptionWasThrownOnGetByIdTest()
            throws NotFoundException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(certificateService.findById(0L))
                .thenReturn(certificateDTO);
        assertEquals(new ResponseData<>(certificateDTO, HttpStatus.OK),
                certificateController.getById(0L));
    }

    @Test
    void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentOnGetByTagTest() {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(bindingResult.hasErrors())
                .thenReturn(true);
        assertThrows(InvalidRequestBodyException.class,
                () -> certificateController.getByTag(tagDTO, bindingResult));
    }

    @Test
    void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundOnGetByTagTest()
            throws NotFoundException {
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doThrow(NotFoundException.class)
                .when(certificateService)
                .findByTag(tagDTO);
        assertThrows(NotFoundException.class,
                () -> certificateController.getByTag(tagDTO, bindingResult));
    }

    @Test
    void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownOnGetByTagTest()
            throws NotFoundException, InvalidRequestBodyException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();
        TagDTO tagDTO =
                ControllerTestDataFactory.createTagDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        when(certificateService.findByTag(tagDTO))
                .thenReturn(List.of(certificateDTO));
        assertEquals(new ResponseData<>(List.of(certificateDTO), HttpStatus.OK),
                certificateController.getByTag(tagDTO, bindingResult));
    }

    @Test
    void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentOnGetBySearchFilterTest() {
        SearchFilterDTO invalidSearchFilterDTO =
                ControllerTestDataFactory.createInvalidSearchFilterDTO();

        when(bindingResult.hasErrors())
                .thenReturn(true);
        assertThrows(InvalidRequestBodyException.class,
                () -> certificateController.getBySearchFilter(
                        invalidSearchFilterDTO, bindingResult));
    }

    @Test
    void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundOnGetBySearchFilterTest()
            throws NotFoundException {
        SearchFilterDTO invalidSearchFilterDTO =
                ControllerTestDataFactory.createInvalidSearchFilterDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doThrow(NotFoundException.class)
                .when(certificateService)
                        .findBySearchFilter(invalidSearchFilterDTO);
        assertThrows(NotFoundException.class,
                () -> certificateController.getBySearchFilter(invalidSearchFilterDTO, bindingResult));
    }

    @Test
    void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownOnGetBySearchFilterTest()
            throws NotFoundException, InvalidRequestBodyException {
        SearchFilterDTO invalidSearchFilterDTO =
                ControllerTestDataFactory.createInvalidSearchFilterDTO();
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        when(certificateService.findBySearchFilter(invalidSearchFilterDTO))
                .thenReturn(List.of(certificateDTO));
        assertEquals(new ResponseData<>(List.of(certificateDTO), HttpStatus.OK),
                certificateController.getBySearchFilter(invalidSearchFilterDTO, bindingResult));
    }

    @Test
    void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentOnGetBySortFilterTest() {
        SortFilterDTO invalidSortFilterDTO =
                ControllerTestDataFactory.createInvalidSortFilterDTO();

        when(bindingResult.hasErrors())
                .thenReturn(true);
        assertThrows(InvalidRequestBodyException.class,
                () -> certificateController.getBySortFilter(invalidSortFilterDTO, bindingResult));
    }
    @Test
    void shouldThrowNotFoundExceptionIfCertificatesWereNotFoundOnGetBySortFilterTest()
            throws NotFoundException {
        SortFilterDTO invalidSortFilterDTO =
                ControllerTestDataFactory.createInvalidSortFilterDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doThrow(NotFoundException.class)
                .when(certificateService)
                        .findBySortFilter(invalidSortFilterDTO);
        assertThrows(NotFoundException.class,
                () -> certificateController.getBySortFilter(invalidSortFilterDTO, bindingResult));
    }

    @Test
    void shouldReturnResponseWithCorrectListIfNoExceptionWasThrownOnGetBySortFilterTest()
            throws NotFoundException, InvalidRequestBodyException {
        SortFilterDTO invalidSortFilterDTO =
                ControllerTestDataFactory.createInvalidSortFilterDTO();
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        when(certificateService.findBySortFilter(invalidSortFilterDTO))
                .thenReturn(List.of(certificateDTO));
        assertEquals(new ResponseData<>(List.of(certificateDTO), HttpStatus.OK),
                certificateController.getBySortFilter(invalidSortFilterDTO, bindingResult));
    }

    @Test
    void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentOnCreateTest() {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(true);
        assertThrows(InvalidRequestBodyException.class,
                () -> certificateController.create(certificateDTO, bindingResult));
    }

    @Test
    void shouldThrowModificationExceptionIfExceptionWasThrownOnCreateTest()
            throws ModificationException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                    .thenReturn(false);
        doThrow(ModificationException.class)
                .when(certificateService)
                        .create(certificateDTO);
        assertThrows(ModificationException.class,
                () -> certificateController.create(certificateDTO, bindingResult));
    }
    @Test
    void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownOnCreateTest()
            throws InvalidRequestBodyException, ModificationException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doNothing()
                .when(certificateService)
                        .create(certificateDTO);
        assertEquals(HttpStatus.OK,
                certificateController.create(certificateDTO, bindingResult).getStatus());
    }

    @Test
    void shouldThrowInvalidRequestBodyExceptionIfFieldErrorsArePresentOnEditTest() {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(true);
        assertThrows(InvalidRequestBodyException.class,
                () -> certificateController.edit(certificateDTO, bindingResult));
    }

    @Test
    void shouldThrowNotFoundExceptionIfCertificateWasNotFoundOnEditTest()
            throws NotFoundException, ModificationException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doThrow(NotFoundException.class)
                .when(certificateService)
                        .update(certificateDTO);
        assertThrows(NotFoundException.class,
                () -> certificateController.edit(certificateDTO, bindingResult));
    }

    @Test
    void shouldThrowModificationExceptionIfExceptionWasThrownOnEditTest()
            throws NotFoundException, ModificationException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        doThrow(ModificationException.class)
                .when(certificateService)
                        .update(certificateDTO);
        assertThrows(ModificationException.class,
                () -> certificateController.edit(certificateDTO, bindingResult));
    }

    @Test
    void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownOnEditTest()
            throws InvalidRequestBodyException, NotFoundException, ModificationException {
        CertificateDTO certificateDTO =
                ControllerTestDataFactory.createCertificateDTO();

        when(bindingResult.hasErrors())
                .thenReturn(false);
        assertEquals(HttpStatus.OK,
                certificateController.edit(certificateDTO, bindingResult).getStatus());
    }
    @Test
    void shouldThrowNotFoundExceptionIfCertificateWasNotFoundOnDeleteByIdTest()
            throws NotFoundException, ModificationException {
        doThrow(NotFoundException.class)
                .when(certificateService)
                        .delete(0L);
        assertThrows(NotFoundException.class,
                () -> certificateController.deleteById(0L));
    }

    @Test
    void shouldThrowModificationExceptionIfExceptionWasThrownOnDeleteByIdTest()
            throws NotFoundException, ModificationException {
        doThrow(ModificationException.class)
                .when(certificateService)
                        .delete(0L);
        assertThrows(ModificationException.class,
                () -> certificateController.deleteById(0L));
    }

    @Test
    void shouldReturnResponseWithCorrectStatusIfNoExceptionWasThrownOnDeleteByIdTest()
            throws NotFoundException, ModificationException {
        doNothing()
                .when(certificateService)
                        .delete(0L);
        assertEquals(HttpStatus.OK,
                certificateController.deleteById(0L).getStatus());
    }
}
