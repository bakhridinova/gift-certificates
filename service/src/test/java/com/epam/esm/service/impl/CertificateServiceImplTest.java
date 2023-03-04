package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.ServiceTestDataFactory;
import com.epam.esm.util.mapper.CertificateMapper;
import com.epam.esm.util.mapper.FilterMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * certificate service tests
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class CertificateServiceImplTest {
    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private CertificateMapper certificateMapper;
    @Mock
    private FilterMapper filterMapper;
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private CertificateService certificateService;

    @BeforeEach
    public void setUp() {
        certificateMapper = mock(CertificateMapper.class);
        filterMapper = mock(FilterMapper.class);
        tagMapper = mock(TagMapper.class);
        certificateRepository = mock(CertificateRepositoryImpl.class);
        certificateService = new CertificateServiceImpl(certificateRepository, certificateMapper, filterMapper, tagMapper);
    }

    @Test
    public void shouldThrowNotFoundExceptionIfDataAccessExceptionWasThrownOnFindAllTest() {
        doThrow(new DataAccessException("") {})
                .when(certificateRepository).findAll();
        assertThrows(NotFoundException.class, () ->
                certificateService.findAll());
    }

    @Test
    public void shouldReturnCorrectListOfCertificatesIfNoExceptionWasThrownOnFindAllTest() throws NotFoundException {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateRepository.findAll())
                .thenReturn(List.of(certificate));
        when(certificateMapper.toCertificateDTO(certificate))
                .thenReturn(certificateDTO);
        assertEquals(List.of(certificateDTO),
                certificateService.findAll());
    }


    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnFindByIdTest() {
        doThrow(EmptyResultDataAccessException.class)
                .when(certificateRepository)
                        .findById(0L);
        assertThrows(NotFoundException.class,
                () -> certificateService.findById(0L));
    }

    @Test
    public void shouldReturnCorrectCertificateIfCertificateWithSuchIdWasFoundOnFindByIdTest() throws NotFoundException {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateRepository.findById(0L))
                .thenReturn(Optional.of(certificate));
        when(certificateMapper.toCertificateDTO(certificate))
                .thenReturn(certificateDTO);
        assertEquals(certificateDTO,
                certificateService.findById(0L));
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnFindByTagTest() {
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagMapper.toTag(tagDTO))
                .thenReturn(tag);
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                        .findByTag(tag);
        assertThrows(NotFoundException.class, () ->
                certificateService.findByTag(tagDTO));
    }

    @Test
    public void shouldReturnEmptyListIfCertificatesWithSuchTagWereNotFoundOnFindByTagTest() throws NotFoundException {
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagMapper.toTag(tagDTO))
                .thenReturn(tag);
        when(certificateRepository.findByTag(tag))
                .thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(),
                certificateService.findByTag(tagDTO));
    }

    @Test
    public void shouldReturnCorrectListIfCertificatesWithSuchTagWereFoundOnFindByTagTest() throws NotFoundException {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();
        Tag tag =
                ServiceTestDataFactory.createTag();
        TagDTO tagDTO =
                ServiceTestDataFactory.createTagDTO();

        when(tagMapper.toTag(tagDTO))
                .thenReturn(tag);
        when(certificateRepository.findByTag(tag))
                .thenReturn(List.of(certificate));
        when(certificateMapper.toCertificateDTO(certificate))
                .thenReturn(certificateDTO);
        assertEquals(List.of(certificateDTO),
                certificateService.findByTag(tagDTO));
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnFindBySearchFilterTest() {
        SearchFilter searchFilter =
                ServiceTestDataFactory.createSearchFilter();
        SearchFilterDTO searchFilterDTO =
                ServiceTestDataFactory.createSearchFilterDTO();

        when(filterMapper.toSearchFilter(searchFilterDTO))
                .thenReturn(searchFilter);
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                      .findBySearchFilter(searchFilter);
        assertThrows(NotFoundException.class,
                () -> certificateService.findBySearchFilter(searchFilterDTO));
    }

    @Test
    public void shouldReturnEmptyListIfCertificatesWithSuchSearchValueWereNotFoundOnFindBySearchFilterTest() throws NotFoundException {
        SearchFilter searchFilter =
                ServiceTestDataFactory.createSearchFilter();
        SearchFilterDTO searchFilterDTO =
                ServiceTestDataFactory.createSearchFilterDTO();

        when(filterMapper.toSearchFilter(searchFilterDTO))
                .thenReturn(searchFilter);
        when(certificateRepository.findBySearchFilter(searchFilter))
                .thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(),
                certificateService.findBySearchFilter(searchFilterDTO));
    }

    @Test
    public void shouldReturnCorrectListIfCertificatesWithSuchSearchValueWereFoundOnFindBySearchFilterTest() throws NotFoundException {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();
        SearchFilter searchFilter =
                ServiceTestDataFactory.createSearchFilter();
        SearchFilterDTO searchFilterDTO =
                ServiceTestDataFactory.createSearchFilterDTO();

        when(filterMapper.toSearchFilter(searchFilterDTO))
                .thenReturn(searchFilter);
        when(certificateRepository.findBySearchFilter(searchFilter))
                .thenReturn(List.of(certificate));
        when(certificateMapper.toCertificateDTO(certificate))
                .thenReturn(certificateDTO);
        assertEquals(List.of(certificateDTO),
                certificateService.findBySearchFilter(searchFilterDTO));
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnFindBySortFilterTest() {
        SortFilter sortFilter =
                ServiceTestDataFactory.createSortFilter();
        SortFilterDTO sortFilterDTO =
                ServiceTestDataFactory.createSortFilterDTO();

        when(filterMapper.toSortFilter(sortFilterDTO))
                .thenReturn(sortFilter);
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                        .findBySortFilter(sortFilter);
        assertThrows(NotFoundException.class,
                () -> certificateService.findBySortFilter(sortFilterDTO));
    }

    @Test
    public void shouldReturnEmptyListIfCertificatesWithSuchSortValueWereNotFoundOnFindBySortFilterTest() throws NotFoundException {
        SortFilter sortFilter =
                ServiceTestDataFactory.createSortFilter();
        SortFilterDTO sortFilterDTO =
                ServiceTestDataFactory.createSortFilterDTO();

        when(filterMapper.toSortFilter(sortFilterDTO))
                .thenReturn(sortFilter);
        when(certificateRepository.findBySortFilter(sortFilter))
                .thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(),
                certificateService.findBySortFilter(sortFilterDTO));
    }

    @Test
    public void shouldReturnCorrectListIfCertificatesWithSuchSortValueWereFoundOnFindBySortFilterTest() throws NotFoundException {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();
        SortFilter sortFilter =
                ServiceTestDataFactory.createSortFilter();
        SortFilterDTO sortFilterDTO =
                ServiceTestDataFactory.createSortFilterDTO();

        when(filterMapper.toSortFilter(sortFilterDTO))
                .thenReturn(sortFilter);
        when(certificateRepository.findBySortFilter(sortFilter))
                .thenReturn(List.of(certificate));
        when(certificateMapper.toCertificateDTO(certificate))
                .thenReturn(certificateDTO);
        assertEquals(List.of(certificateDTO),
                certificateService.findBySortFilter(sortFilterDTO));
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnUpdateTest() {
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        doThrow(EmptyResultDataAccessException.class)
                .when(certificateRepository)
                        .findById(0L);
        assertThrows(NotFoundException.class,
                () -> certificateService.update(certificateDTO));
    }

    @Test
    public void shouldThrowModificationExceptionIfCertificateDataAccessExceptionWasThrownOnUpdateTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateRepository.findById(0L)).
                thenReturn(Optional.of(certificate));
        when(certificateMapper.toCertificate(certificateDTO))
                .thenReturn(certificate);
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                        .update(certificate);
        assertThrows(ModificationException.class,
                () -> certificateService.update(certificateDTO));
    }

    @Test
    public void shouldThrowModificationExceptionIfTagDataAccessExceptionWasThrownOnUpdateTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateRepository.findById(0L)).
                thenReturn(Optional.of(certificate));
        when(certificateMapper.toCertificate(certificateDTO))
                .thenReturn(certificate);
        doNothing()
                .when(certificateRepository)
                        .update(certificate);
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                        .insertTags(certificate);
        assertThrows(ModificationException.class,
                () -> certificateService.update(certificateDTO));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownOnUpdateTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateRepository.findById(0L)).
                thenReturn(Optional.of(certificate));
        doNothing()
                .when(certificateRepository)
                        .update(certificate);
        doNothing()
                .when(certificateRepository)
                        .insertTags(certificate);
        assertDoesNotThrow(() ->
                certificateService.update(certificateDTO));
    }

    @Test
    public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownOnCreateTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateMapper.toCertificate(certificateDTO))
                .thenReturn(certificate);
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                        .insert(certificate);
        assertThrows(ModificationException.class,
                () -> certificateService.create(certificateDTO));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownOnCreateTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        when(certificateMapper.toCertificate(certificateDTO))
                .thenReturn(certificate);
        when(certificateRepository.insert(certificate))
                .thenReturn(0L);
        assertDoesNotThrow(() ->
                certificateService.create(certificateDTO));
    }

    @Test
    public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownOnDeleteTest() {
        doThrow(EmptyResultDataAccessException.class)
                .when(certificateRepository)
                        .findById(0L);
        assertThrows(NotFoundException.class,
                () -> certificateService.delete(0L));
    }

    @Test
    public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownOnDeleteTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();

        when(certificateRepository.findById(0L))
                .thenReturn(Optional.of(certificate));
        doThrow(new DataAccessException("") {})
                .when(certificateRepository)
                        .delete(0L);
        assertThrows(ModificationException.class,
                () -> certificateService.delete(0L));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownOnDeleteTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();

        when(certificateRepository.findById(0L))
                .thenReturn(Optional.of(certificate));
        doNothing()
                .when(certificateRepository)
                        .delete(0L);
        assertDoesNotThrow(() ->
                certificateService.delete(0L));
    }
}