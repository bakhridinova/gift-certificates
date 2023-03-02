package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

public interface CertificateService {
    /**
     * find all existing certificates
     *
     * @return List of certificate if at least one certificate was found, otherwise empty List
     * @throws NotFoundException if DataAccessException was caught
     */
    List<CertificateDTO> findAll() throws NotFoundException;

    /**
     * find certificate by given id
     *
     * @param id Long certificate id
     * @return instance of CertificateDTO if certificate was found
     * @throws NotFoundException if certificate was not found
     */
    CertificateDTO findById(Long id) throws NotFoundException;

    /**
     * find all certificates with given tags
     *
     * @param tag Tag
     * @return List of certificates if at least one certificate was found, otherwise empty List
     * @throws NotFoundException if DataAccessException was caught
     */
    List<CertificateDTO> findByTag(TagDTO tag) throws NotFoundException;

    /**
     * find all certificates with given value
     *
     * @param searchFilter SearchFilterDTO containing search information
     * @return List of certificates matching search parameters if at least one certificate was found, otherwise empty List
     * @throws NotFoundException if DataAccessException was caught
     */
    List<CertificateDTO> findBySearchFilter(SearchFilterDTO searchFilter) throws NotFoundException;

    /**
     * find all certificates and sort by given filter
     *
     * @param sortFilter SortFilterDTO containing sort information
     * @return List of certificates sorted by filter
     * @throws NotFoundException if DataAccessException was caught
     */
    List<CertificateDTO> findBySortFilter(SortFilterDTO sortFilter) throws NotFoundException;

    /**
     * create new certificate and add newly passed tags if such tags are present
     *
     * @param certificate CertificateDTO
     * @throws ModificationException if DataAccessException was caught
     */
    void create(CertificateDTO certificate) throws ModificationException;

    /**
     * update certificate with given id and add newly passed tags if such tags are present
     *
     * @param certificate CertificateDTO
     * @throws NotFoundException if certificate with such id does not exist
     * @throws ModificationException if DataAccessException was caught
     */
    void update(CertificateDTO certificate) throws NotFoundException, ModificationException;

    /**
     * delete certificate with given id
     *
     * @throws NotFoundException if certificate with such id does not exist
     * @throws ModificationException if DataAccessException was caught
     */
    void delete(Long id) throws NotFoundException, ModificationException;
}
