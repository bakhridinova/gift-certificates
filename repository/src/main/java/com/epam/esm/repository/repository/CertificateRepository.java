package com.epam.esm.repository.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository for manipulating Certificate data in table certificate
 */

public interface CertificateRepository extends BaseRepository<Certificate> {
    /**
     * retrieve all certificate data
     *
     * @return List of all certificates
     */
    @Override
    List<Certificate> findAll();

    /**
     * retrieve certificate data according to id
     *
     * @param id Long certificate id
     * @return Optional.of(Certificate) if certificate with such id exists, otherwise Optional.empty()
     */
    @Override
    Optional<Certificate> findById(Long id);

    /**
     * retrieve all certificates data according to tag
     *
     * @param tag Tag
     * @return List of all certificates with given tags
     */
    List<Certificate> findByTag(Tag tag);

    /**
     * retrieve all certificate data according to searchProperty
     *
     * @param searchFilter SearchFilter
     * @return List of all certificates with given searchProperty
     */
    List<Certificate> findBySearchFilter(SearchFilter searchFilter);

    /**
     * retrieve all certificate data sorted according to sortProperty
     *
     * @param sortFilter SortFilter
     * @return List of all certificates with given searchProperty
     */
    List<Certificate> findBySortFilter(SortFilter sortFilter);

    /**
     * insert new certificate
     *
     * @param certificate Certificate
     * @return Long id of inserted certificate
     */
    @Override
    Long insert(Certificate certificate);

    /**
     * update existing certificate data
     *
     * @param certificate Certificate
     */
    @Override
    void update(Certificate certificate);

    /**
     * insert tags to certificate
     *
     * @param certificate Certificate
     */
    void insertTags(Certificate certificate);

    /**
     * delete certificate
     *
     * @param id Long certificate id
     */
    @Override
    void delete(Long id);
}
