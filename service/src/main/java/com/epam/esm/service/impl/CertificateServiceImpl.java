package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.mapper.CertificateMapper;
import com.epam.esm.util.mapper.FilterMapper;
import com.epam.esm.util.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.epam.esm.util.ExceptionMessage.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final CertificateMapper certificateMapper;
    private final FilterMapper filterMapper;
    private final TagMapper tagMapper;

    @Override
    public List<CertificateDTO> findAll()
            throws NotFoundException {
        try {
            log.info("********** finding all certificates...");
            return certificateRepository.findAll().stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException ex) {
            log.error("failed to find certificates, cause {}", ex.getMessage());
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, ex);
        }
    }

    @Override
    public CertificateDTO findById(Long id) throws NotFoundException {
        log.info("********** finding certificate by id...");
        try {
            return certificateMapper.toCertificateDTO(certificateRepository.findById(id).get());
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to find certificate by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        }
    }

    @Override
    public List<CertificateDTO> findByTag(TagDTO tag) throws NotFoundException {
        try {
            log.info("********** finding certificates by tags...");
            return certificateRepository.findByTag(tagMapper.toTag(tag)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException ex) {
            log.error("failed to find certificates by tags, cause {}", ex.getMessage());
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, ex);
        }
    }

    @Override
    public List<CertificateDTO> findBySearchFilter(SearchFilterDTO searchFilter) throws NotFoundException {
        try {
            log.info("********** finding certificates by search filter...");
            return certificateRepository.findBySearchFilter(filterMapper.toSearchFilter(searchFilter)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException ex) {
            log.error("failed to find certificates by search filter, cause {}", ex.getMessage());
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, ex);
        }
    }

    @Override
    public List<CertificateDTO> findBySortFilter(SortFilterDTO sortFilter) throws NotFoundException {
        try {
            log.info("********** finding certificates by sort filter...");
            return certificateRepository.findBySortFilter(filterMapper.toSortFilter(sortFilter)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException ex) {
            log.error("failed to find certificates by sort filter, cause {}", ex.getMessage());
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, ex);
        }
    }

    @Override
    public void create(CertificateDTO certificatedto) throws ModificationException {
        Certificate certificate = certificateMapper.toCertificate(certificatedto);
        try {
            log.info("********** creating new certificate...");
            certificate.setId(certificateRepository.insert(certificate));
            Arrays.stream(certificatedto.getTags()).forEach(tag -> tag.setId(tagRepository.insert(tagMapper.toTag(tag))));
            certificateRepository.insertTags(certificate);
        } catch (DataAccessException ex) {
            log.error("failed to create certificate, cause {}", ex.getMessage());
            throw new ModificationException(CERTIFICATE_CREATE_FAILED, ex);
        }
    }

    @Override
    public void update(CertificateDTO certificatedto) throws NotFoundException, ModificationException {
        long id = certificatedto.getId();
        try {
            log.info("********** editing certificate...");
            certificateRepository.findById(id);
            Certificate certificate = certificateMapper.toCertificate(certificatedto);
            certificateRepository.update(certificate);
            Arrays.stream(certificatedto.getTags()).forEach(tag -> tag.setId(tagRepository.insert(tagMapper.toTag(tag))));
            certificateRepository.insertTags(certificate);
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to find certificate by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        } catch (DataAccessException ex) {
            log.error("failed to update certificate with id {}, cause {}", id, ex.getMessage());
            throw new ModificationException(CERTIFICATE_UPDATE_FAILED, ex);
        }
    }

    @Override
    public void delete(Long id) throws NotFoundException, ModificationException {
        try {
            log.info("********** deleting certificate...");
            certificateRepository.findById(id);
            certificateRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to find certificate by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        } catch (DataAccessException ex) {
            log.error("failed to delete certificate with id {}, cause {}", id, ex.getMessage());
            throw new ModificationException(CERTIFICATE_DELETE_FAILED, ex);
        }
    }
}
