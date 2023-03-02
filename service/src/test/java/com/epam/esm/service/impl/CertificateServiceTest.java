package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.mapper.CertificateMapper;
import com.epam.esm.util.mapper.FilterMapper;
import com.epam.esm.util.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

import static com.epam.esm.util.ServiceTestEntityHolder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * certificate service tests
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class CertificateServiceTest {
    @Mock
    private TagRepository tagRepository;
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
        tagRepository = mock(TagRepositoryImpl.class);
        certificateRepository = mock(CertificateRepositoryImpl.class);
        certificateService = new CertificateServiceImpl(certificateRepository, tagRepository, certificateMapper, filterMapper, tagMapper);
    }

    @Nested
    class FindAllTest {
        @Test
        public void shouldThrowNotFoundExceptionIfDataAccessExceptionWasThrownTest() {
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository).findAll();
            assertThrows(NotFoundException.class, () ->
                   certificateService.findAll());
        }

        @Test
        public void shouldReturnCorrectListOfCertificatesIfNoExceptionWasThrownTest() throws NotFoundException {
            when(certificateRepository.findAll())
                    .thenReturn(List.of(certificate));
            when(certificateMapper.toCertificateDTO(certificate))
                    .thenReturn(certificateDTO);
            assertEquals(List.of(certificateDTO),
                    certificateService.findAll());
        }
    }

    @Nested
    class FindByIdTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            doThrow(EmptyResultDataAccessException.class)
                    .when(certificateRepository)
                            .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> certificateService.findById(0L));
        }

        @Test
        public void shouldReturnCorrectCertificateIfCertificateWithSuchIdWasFoundTest() throws NotFoundException {
            when(certificateRepository.findById(0L))
                    .thenReturn(Optional.of(certificate));
            when(certificateMapper.toCertificateDTO(certificate))
                    .thenReturn(certificateDTO);
            assertEquals(certificateDTO,
                    certificateService.findById(0L));
        }
    }

    @Nested
    class FindByTagTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            when(tagMapper.toTag(tagDTO))
                    .thenReturn(tag);
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository)
                            .findByTag(tag);
            assertThrows(NotFoundException.class, () ->
                    certificateService.findByTag(tagDTO));
        }

        @Test
        public void shouldReturnEmptyListIfCertificatesWithSuchTagWereNotFoundTest() throws NotFoundException {
            when(tagMapper.toTag(tagDTO))
                    .thenReturn(tag);
            when(certificateRepository.findByTag(tag))
                    .thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<>(),
                    certificateService.findByTag(tagDTO));
        }

        @Test
        public void shouldReturnCorrectListIfCertificatesWithSuchTagWereFoundTest() throws NotFoundException {
            when(tagMapper.toTag(tagDTO))
                    .thenReturn(tag);
            when(certificateRepository.findByTag(tag))
                    .thenReturn(List.of(certificate));
            when(certificateMapper.toCertificateDTO(certificate))
                    .thenReturn(certificateDTO);
            assertEquals(List.of(certificateDTO),
                    certificateService.findByTag(tagDTO));
        }
    }

    @Nested
    class FindBySearchFilterTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            when(filterMapper.toSearchFilter(searchFilterDTO))
                    .thenReturn(searchFilter);
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository)
                            .findBySearchFilter(searchFilter);
            assertThrows(NotFoundException.class,
                    () -> certificateService.findBySearchFilter(searchFilterDTO));
        }

        @Test
        public void shouldReturnEmptyListIfCertificatesWithSuchSearchValueWereNotFoundTest() throws NotFoundException {
            when(filterMapper.toSearchFilter(searchFilterDTO))
                    .thenReturn(searchFilter);
            when(certificateRepository.findBySearchFilter(searchFilter))
                    .thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<>(),
                    certificateService.findBySearchFilter(searchFilterDTO));
        }

        @Test
        public void shouldReturnCorrectListIfCertificatesWithSuchSearchValueWereFoundTest() throws NotFoundException {
            when(filterMapper.toSearchFilter(searchFilterDTO))
                    .thenReturn(searchFilter);
            when(certificateRepository.findBySearchFilter(searchFilter))
                    .thenReturn(List.of(certificate));
            when(certificateMapper.toCertificateDTO(certificate))
                    .thenReturn(certificateDTO);
            assertEquals(List.of(certificateDTO),
                    certificateService.findBySearchFilter(searchFilterDTO));
        }
    }

    @Nested
    class FindBySortFilterTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            when(filterMapper.toSortFilter(sortFilterDTO))
                    .thenReturn(sortFilter);
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository)
                            .findBySortFilter(sortFilter);
            assertThrows(NotFoundException.class,
                    () -> certificateService.findBySortFilter(sortFilterDTO));
        }

        @Test
        public void shouldReturnEmptyListIfCertificatesWithSuchSortValueWereNotFoundTest() throws NotFoundException {
            when(filterMapper.toSortFilter(sortFilterDTO))
                    .thenReturn(sortFilter);
            when(certificateRepository.findBySortFilter(sortFilter))
                    .thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<>(),
                    certificateService.findBySortFilter(sortFilterDTO));
        }

        @Test
        public void shouldReturnCorrectListIfCertificatesWithSuchSortValueWereFoundTest() throws NotFoundException {
            when(filterMapper.toSortFilter(sortFilterDTO))
                    .thenReturn(sortFilter);
            when(certificateRepository.findBySortFilter(sortFilter))
                    .thenReturn(List.of(certificate));
            when(certificateMapper.toCertificateDTO(certificate))
                    .thenReturn(certificateDTO);
            assertEquals(List.of(certificateDTO),
                    certificateService.findBySortFilter(sortFilterDTO));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            doThrow(EmptyResultDataAccessException.class)
                    .when(certificateRepository)
                            .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> certificateService.update(certificateDTO));
        }

        @Test
        public void shouldThrowModificationExceptionIfCertificateDataAccessExceptionWasThrownTest() {
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
        public void shouldThrowModificationExceptionIfTagDataAccessExceptionWasThrownTest() {
            when(certificateRepository.findById(0L)).
                    thenReturn(Optional.of(certificate));
            when(certificateMapper.toCertificate(certificateDTO))
                    .thenReturn(certificate);
            doNothing()
                    .when(certificateRepository).update(certificate);
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository)
                            .insertTags(certificate);
            assertThrows(ModificationException.class,
                    () -> certificateService.update(certificateDTO));
        }

        @Test
        public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownTest() {
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
    }

    @Nested
    class CreateTest {
        @Test
        public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownTest(){
            when(certificateMapper.toCertificate(certificateDTO))
                    .thenReturn(certificate);
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository)
                            .insert(certificate);
            assertThrows(ModificationException.class,
                    () -> certificateService.create(certificateDTO));
        }

        @Test
        public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownTest() {
            when(certificateMapper.toCertificate(certificateDTO))
                    .thenReturn(certificate);
            when(certificateRepository.insert(certificate))
                    .thenReturn(0L);
            assertDoesNotThrow(() ->
                    certificateService.create(certificateDTO));
        }
    }

    @Nested
    class DeleteTest {
        @Test
        public void shouldThrowNotFoundExceptionIfEmptyResultDataAccessExceptionWasThrownTest() {
            doThrow(EmptyResultDataAccessException.class)
                    .when(certificateRepository)
                            .findById(0L);
            assertThrows(NotFoundException.class,
                    () -> certificateService.delete(0L));
        }

        @Test
        public void shouldThrowModificationExceptionIfDataAccessExceptionWasThrownTest() {
            when(certificateRepository.findById(0L))
                    .thenReturn(Optional.of(certificate));
            doThrow(new DataAccessException("") {})
                    .when(certificateRepository)
                    .delete(0L);
            assertThrows(ModificationException.class,
                    () -> certificateService.delete(0L));
        }

        @Test
        public void shouldNotThrowAnyExceptionIfNoExceptionWasThrownTest() {
            when(certificateRepository.findById(0L))
                    .thenReturn(Optional.of(certificate));
            doNothing()
                    .when(certificateRepository)
                            .delete(0L);
            assertDoesNotThrow(() ->
                    certificateService.delete(0L));
        }
    }
}