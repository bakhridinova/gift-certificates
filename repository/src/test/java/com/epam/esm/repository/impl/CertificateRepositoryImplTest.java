package com.epam.esm.repository.impl;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.entity.filter.sort.SortOrder;
import com.epam.esm.entity.filter.sort.SortType;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.util.RepositoryTestDataFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * certificate repository tests
 */

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class CertificateRepositoryImplTest {
    private final CertificateRepository certificateRepository;

    @Mock
    private final CertificateRepository mockCertificateRepository;

    @Autowired
    public CertificateRepositoryImplTest(CertificateRepository certificateRepository) {
        mockCertificateRepository = mock(CertificateRepositoryImpl.class);
        this.certificateRepository = certificateRepository;
    }

    @Test
    @Order(1)
    void shouldReturnCorrectListOfCertificatesOnFindAllTest() {
        IntStream.range(1, 5).forEachOrdered(i -> {
            Certificate certificate =
                    RepositoryTestDataFactory.createCertificate(
                            "name" + i, "description" + i);
            Tag tag =
                    RepositoryTestDataFactory.createTag(
                            "name" + i);

            certificate.setTags(Set.of(tag));
            certificate.setId(certificateRepository.insert(certificate));
            certificateRepository.insertTags(certificate);
        });

        assertEquals(4, certificateRepository.findAll().size());
    }

    @Test
    @Order(2)
    void shouldThrowDataAccessExceptionOnFindAllTest() {
        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .findAll();
        assertThrows(DataAccessException.class,
                mockCertificateRepository::findAll);
    }

    @Test
    @Order(3)
    void shouldReturnCorrectOptionalIfCertificateWasFoundOnFindByIdTest() {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(1L);
        assertTrue(optionalCertificate.isPresent());
        Certificate certificate = optionalCertificate.get();

        assertEquals(1L, certificate.getId());
        assertEquals("name1", certificate.getName());
        assertEquals("description1", certificate.getDescription());
        assertEquals(0.0, certificate.getPrice());
        assertEquals(0, certificate.getDuration());
    }

    @Test
    @Order(4)
    void shouldThrowEmptyResultDataAccessExceptionIfCertificateWasNotFoundOnFindByIdTest() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> certificateRepository.findById(0L));
    }

    @Test
    @Order(5)
    void shouldThrowDataAccessExceptionOnFindByIdTest() {
        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .findById(anyLong());
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.findById(anyLong()));
    }

    @Test
    @Order(6)
    void shouldReturnCorrectListIfCertificatesWithSuchTagWereFoundOnFindByTagTest() {
        for (int i = 1; i < 5; i++) {
            Tag tag = RepositoryTestDataFactory.createTag("name" + i);
            assertEquals(1, certificateRepository.findByTag(tag).size());
        }
    }

    @Test
    @Order(7)
    void shouldReturnEmptyListIfCertificatesWithSuchTagWereNotFoundOnFindByTagTest() {
        Tag tag = RepositoryTestDataFactory.createTag("no name");
        assertEquals(0, certificateRepository.findByTag(tag).size());
    }

    @Test
    @Order(8)
    void shouldThrowDataAccessExceptionOnFindByTagTest() {
        Tag tag = RepositoryTestDataFactory.createTag("some name");
        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .findByTag(tag);
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.findByTag(tag));
    }

    @Test
    @Order(9)
    void shouldReturnCorrectListIfCertificatesWithSuchNameWereFoundOnFindBySearchFilterTest() {
        SearchFilter searchFilter =
                RepositoryTestDataFactory.createSearchFilter(
                        "name", SearchType.NAME);

        assertEquals(4, certificateRepository
                .findBySearchFilter(searchFilter).size());
    }

    @Test
    @Order(10)
    void shouldReturnEmptyListIfCertificatesWithSuchNameWereNotFoundOnFindBySearchFilterTest() {
        SearchFilter searchFilter =
                RepositoryTestDataFactory.createSearchFilter(
                        "no name", SearchType.NAME);

        assertEquals(0, certificateRepository
                .findBySearchFilter(searchFilter).size());
    }

    @Test
    @Order(11)
    void shouldReturnCorrectListIfCertificatesWithSuchDescriptionWereFoundOnFindBySearchFilterTest() {
        SearchFilter searchFilter =
                RepositoryTestDataFactory.createSearchFilter(
                        "description", SearchType.DESCRIPTION);

        assertEquals(4, certificateRepository
                .findBySearchFilter(searchFilter).size());
    }

    @Test
    @Order(12)
    void shouldReturnEmptyListIfCertificatesWithSuchDescriptionWereNotFoundOnFindBySearchFilterTest() {
        SearchFilter searchFilter =
                RepositoryTestDataFactory.createSearchFilter(
                        "no description", SearchType.DESCRIPTION);

        assertEquals(0, certificateRepository
                .findBySearchFilter(searchFilter).size());
    }

    @Test
    @Order(13)
    void shouldThrowDataAccessExceptionFindBySearchFilterTest() {
        SearchFilter searchFilter =
                RepositoryTestDataFactory.createSearchFilter(
                        "some description", SearchType.DESCRIPTION);

        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .findBySearchFilter(searchFilter);
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.findBySearchFilter(searchFilter));
    }

    @Test
    @Order(14)
    void shouldReturnCorrectListOfCertificatesSortedByNameAscendingTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.NAME, SortOrder.ASC);
        List<Certificate> certificates =
                certificateRepository.findBySortFilter(sortFilter);

        IntStream.range(1, certificates.size())
                .mapToObj(i -> certificates.get(i - 1).getName()
                        .compareTo(certificates.get(i).getName()) < 0)
                .forEachOrdered(Assertions::assertTrue);
    }

    @Test
    @Order(15)
    void shouldReturnCorrectListOfCertificatesSortedByNameDescendingTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.NAME, SortOrder.DESC);
        List<Certificate> certificates =
                certificateRepository.findBySortFilter(sortFilter);

        IntStream.range(1, certificates.size())
                .mapToObj(i -> certificates.get(i - 1).getName()
                        .compareTo(certificates.get(i).getName()) > 0)
                .forEachOrdered(Assertions::assertTrue);
    }

    @Test
    @Order(16)
    void shouldReturnCorrectListOfCertificatesSortedByCreateDateAscendingTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.CREATE_DATE, SortOrder.ASC);
        List<Certificate> certificates =
                certificateRepository.findBySortFilter(sortFilter);

        IntStream.range(1, certificates.size())
                .mapToObj(i -> certificates.get(i - 1).getCreateDate()
                        .isBefore(certificates.get(i).getCreateDate()))
                .forEachOrdered(Assertions::assertTrue);
    }

    @Test
    @Order(17)
    void shouldReturnCorrectListOfCertificatesSortedByCreateDateDescendingTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.CREATE_DATE, SortOrder.DESC);
        List<Certificate> certificates =
                certificateRepository.findBySortFilter(sortFilter);

        IntStream.range(1, certificates.size())
                .mapToObj(i -> certificates.get(i - 1).getCreateDate()
                        .isAfter(certificates.get(i).getCreateDate()))
                .forEachOrdered(Assertions::assertTrue);
    }

    @Test
    @Order(18)
    void shouldReturnCorrectListOfCertificatesSortedByLastUpdateDateAscendingTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.LAST_UPDATE_DATE, SortOrder.ASC);
        List<Certificate> certificates =
                certificateRepository.findBySortFilter(sortFilter);

        IntStream.range(1, certificates.size())
                .mapToObj(i -> certificates.get(i - 1).getLastUpdateDate()
                        .isBefore(certificates.get(i).getLastUpdateDate()))
                .forEachOrdered(Assertions::assertTrue);
    }

    @Test
    @Order(19)
    void shouldReturnCorrectListOfCertificatesSortedByLastUpdateDateDescendingTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.LAST_UPDATE_DATE, SortOrder.DESC);
        List<Certificate> certificates =
                certificateRepository.findBySortFilter(sortFilter);

        IntStream.range(1, certificates.size())
                .mapToObj(i -> certificates.get(i - 1).getLastUpdateDate()
                        .isAfter(certificates.get(i).getLastUpdateDate()))
                .forEachOrdered(Assertions::assertTrue);
    }

    @Test
    @Order(20)
    void shouldThrowDataAccessExceptionOnFindBySortFilterTest() {
        SortFilter sortFilter =
                RepositoryTestDataFactory.createSortFilter(
                        SortType.NAME, SortOrder.ASC);

        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .findBySortFilter(sortFilter);
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.findBySortFilter(sortFilter));
    }

    @Test
    @Order(21)
    void shouldNotThrowAnythingIfCertificateWasInsertedTest() {
        int size = certificateRepository.findAll().size();

        Certificate certificate =
                RepositoryTestDataFactory.createCertificate(
                        "some name", "some description");
        Long id = certificateRepository.insert(certificate);

        Optional<Certificate> optionalCertificate =
                certificateRepository.findById(id);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(++size, certificateRepository.findAll().size());
    }

    @Test
    @Order(22)
    void shouldThrowDataAccessExceptionIfNullObjectIsInsertedTest() {
        Certificate nullCertificate =
                RepositoryTestDataFactory.createNullCertificate();

        assertThrows(DataAccessException.class,
                () -> certificateRepository.insert(nullCertificate));
    }

    @Test
    @Order(23)
    void shouldThrowDataAccessExceptionOnInsertTest() {
        Certificate certificate =
                RepositoryTestDataFactory.createCertificate(
                        "some name", "some description");

        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .insert(certificate);
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.insert(certificate));
    }

    @Test
    @Order(24)
    void shouldUpdateOnlyNotNullCertificateFieldsTest() {
        Optional<Certificate> optionalCertificate =
                certificateRepository.findById(5L);
        assertTrue(optionalCertificate.isPresent());

        Certificate certificate = optionalCertificate.get();
        certificate.setPrice(10.0);
        certificateRepository.update(certificate);
        optionalCertificate = certificateRepository.findById(5L);
        assertTrue(optionalCertificate.isPresent());
        assertEquals(10.0, certificate.getPrice());
    }

    @Test
    @Order(25)
    void shouldNotUpdateIfAllCertificateFieldsAreNullTest() {
        Optional<Certificate> beforeUpdate =
                certificateRepository.findById(5L);
        assertTrue(beforeUpdate.isPresent());

        Certificate nullCertificate =
                RepositoryTestDataFactory.createNullCertificate();
        Certificate certificateBeforeUpdate = beforeUpdate.get();
        nullCertificate.setId(certificateBeforeUpdate.getId());
        certificateRepository.update(nullCertificate);

        Optional<Certificate> afterUpdate =
                certificateRepository.findById(5L);
        assertTrue(afterUpdate.isPresent());

        Certificate certificateAfterUpdate = afterUpdate.get();
        assertEquals(certificateBeforeUpdate.getName(), certificateAfterUpdate.getName());
        assertEquals(certificateBeforeUpdate.getDescription(), certificateAfterUpdate.getDescription());
        assertEquals(certificateBeforeUpdate.getDuration(), certificateAfterUpdate.getDuration());
        assertEquals(certificateBeforeUpdate.getPrice(), certificateAfterUpdate.getPrice());

    }

    @Test
    @Order(26)
    void shouldThrowDataAccessExceptionOnUpdateTest() {
        Certificate certificate =
                RepositoryTestDataFactory.createCertificate(
                        "some name", "some description");

        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .update(certificate);
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.update(certificate));
    }

    @Test
    @Order(27)
    void shouldNotThrowAnythingIfTagsWereInsertedTest() {
        Optional<Certificate> optionalCertificate =
                certificateRepository.findById(5L);
        assertTrue(optionalCertificate.isPresent());

        Certificate certificate = optionalCertificate.get();
        int size = certificate.getTags().size();

        Tag tag = RepositoryTestDataFactory.createTag("name");
        tag.setId(5L);
        certificate.setTags(Set.of(tag));

        certificateRepository.insertTags(certificate);
        assertEquals(++size, certificate.getTags().size());
    }

    @Test
    @Order(28)
    void shouldThrowDataAccessExceptionIfNullObjectsAreInsertedTest() {
        Optional<Certificate> optionalCertificate =
                certificateRepository.findById(5L);
        assertTrue(optionalCertificate.isPresent());

        Tag nullTag = RepositoryTestDataFactory.createNullTag();
        Certificate certificate = optionalCertificate.get();
        certificate.setTags(Set.of(nullTag));

        assertThrows(DataAccessException.class,
                () -> certificateRepository.insertTags(certificate));
    }

    @Test
    @Order(29)
    void shouldThrowDataAccessExceptionOnInsertTagsTest() {
        Certificate certificate =
                RepositoryTestDataFactory.createCertificate(
                        "some name", "some description");

        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .insertTags(certificate);
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.insertTags(certificate));
    }

    @Test
    @Order(30)
    void shouldNotThrowAnythingIfCertificateWasDeletedTest() {
        int size = certificateRepository.findAll().size();
        certificateRepository.delete(5L);

        assertEquals(--size, certificateRepository.findAll().size());
        assertThrows(EmptyResultDataAccessException.class,
                () -> certificateRepository.findById(5L));
    }

    @Test
    @Order(31)
    void shouldThrowDataAccessExceptionOnDeleteTest() {
        doThrow(new DataAccessException("") {})
                .when(mockCertificateRepository)
                        .delete(anyLong());
        assertThrows(DataAccessException.class,
                () -> mockCertificateRepository.delete(anyLong()));
    }
}