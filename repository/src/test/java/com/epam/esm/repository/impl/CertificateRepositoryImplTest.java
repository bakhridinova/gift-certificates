package com.epam.esm.repository.impl;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchPlace;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.repository.repository.CertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.RepositoryTestEntityHolder.certificate;
import static com.epam.esm.util.RepositoryTestEntityHolder.tag;
import static org.junit.jupiter.api.Assertions.*;

/**
 * certificate repository tests
 */

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class CertificateRepositoryImplTest extends AbstractIntegrationTest {

    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateRepositoryImplTest(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Test
    void shouldReturnCorrectListOfCertificatesTest() {
        List<Certificate> certificates = certificateRepository.findAll();
        assertNotNull(certificates);
        assertEquals(4, certificates.size());
    }

    @Test
    void shouldReturnCorrectOptionalIfCertificateWasFoundTest() {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(1L);
        assertNotNull(optionalCertificate);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(1L, optionalCertificate.get().getId());
        assertEquals("name", optionalCertificate.get().getName());
        assertEquals("description", optionalCertificate.get().getDescription());
        assertEquals(0.0, optionalCertificate.get().getPrice());
        assertEquals(0, optionalCertificate.get().getDuration());
    }

    @Test
    public void shouldThrowEmptyResultDataAccessExceptionIfTagWasNotFoundTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> certificateRepository.findById(0L));
    }

    @Test
    void shouldReturnCorrectListOfCertificatesByTagTest() {
        List<Certificate> certificates = certificateRepository.findByTag(tag);
        assertEquals(1, certificates.size());
    }

    @Test
    void shouldReturnCorrectListOfCertificatesBySearchFilterTest() {
        SearchFilter searchFilter = SearchFilter
                .builder()
                .searchValue("no name")
                .searchType(SearchType.NAME)
                .searchPlace(SearchPlace.ENDS_WITH)
                .build();

        assertEquals(0, certificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchPlace(SearchPlace.BEGINS_WITH);
        assertEquals(0, certificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchValue("name");
        assertEquals(4, certificateRepository.findBySearchFilter(searchFilter).size());

        searchFilter = SearchFilter
                .builder()
                .searchValue("no description")
                .searchType(SearchType.DESCRIPTION)
                .searchPlace(SearchPlace.ENDS_WITH)
                .build();

        assertEquals(0, certificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchPlace(SearchPlace.BEGINS_WITH);
        assertEquals(0, certificateRepository.findBySearchFilter(searchFilter).size());
        searchFilter.setSearchValue("description");
        assertEquals(4, certificateRepository.findBySearchFilter(searchFilter).size());
    }

    @Test
    public void shouldNotThrowAnythingIfCertificateWasInsertedTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> certificateRepository.findById(5L));
        int size = certificateRepository.findAll().size();

        Long id = certificateRepository.insert(certificate);
        Optional<Certificate> optionalCertificate = certificateRepository.findById(id);
        assertNotNull(optionalCertificate);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(++size, certificateRepository.findAll().size());
    }

    @Test
    void shouldNotThrowAnythingIfCertificateWasUpdatedTest() {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());
        optionalCertificate.get().setPrice(10.0);
        certificateRepository.update(optionalCertificate.get());
        optionalCertificate = certificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(10.0, optionalCertificate.get().getPrice());
    }

    @Test
    void shouldNotThrowAnythingIfCertificateWasDeletedTest() {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());

        int size = certificateRepository.findAll().size();
        certificateRepository.delete(1L);
        assertEquals(--size, certificateRepository.findAll().size());
        assertThrows(EmptyResultDataAccessException.class,
                () -> certificateRepository.findById(1L));
    }
}