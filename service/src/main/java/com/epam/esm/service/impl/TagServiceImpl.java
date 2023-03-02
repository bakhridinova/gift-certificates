package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.ExceptionMessage.TAGS_NOT_FOUND;
import static com.epam.esm.util.ExceptionMessage.TAG_CREATE_FAILED;
import static com.epam.esm.util.ExceptionMessage.TAG_DELETE_FAILED;
import static com.epam.esm.util.ExceptionMessage.TAG_ID_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagDTO> findAll() throws NotFoundException {
        try {
            log.info("********** finding all tags...");
            return tagRepository.findAll().stream().map(tagMapper::toTagDTO).toList();
        } catch (DataAccessException ex) {
            log.error("failed to find tags, cause {}", ex.getMessage());
            throw new NotFoundException(TAGS_NOT_FOUND, ex);
        }
    }
    @Override
    public TagDTO findById(Long id) throws NotFoundException {
        try {
            log.info("********** finding tag by id...");
           return tagMapper.toTagDTO(tagRepository.findById(id).get());
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to find tag by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(TAG_ID_NOT_FOUND, id);
        }
    }

    @Override
    public void create(TagDTO tagdto) throws ModificationException {
        try {
            log.info("********** creating new tag...");
            tagRepository.insert(tagMapper.toTag(tagdto));
        } catch (DataAccessException ex) {
            log.error("failed to create tag, cause {}", ex.getMessage());
            throw new ModificationException(TAG_CREATE_FAILED, ex);
        }
    }

    @Override
    public void delete(Long id)
            throws NotFoundException, ModificationException {
        try {
            log.info("*********** deleting tag...");
            tagRepository.findById(id);
            tagRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to find certificate by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(TAG_ID_NOT_FOUND, id);
        } catch (DataAccessException ex) {
            log.error("failed to delete certificate with id {}, cause {}", id, ex.getMessage());
            throw new ModificationException(TAG_DELETE_FAILED, ex);
        }
    }
}
