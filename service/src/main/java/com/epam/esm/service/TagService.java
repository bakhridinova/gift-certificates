package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

public interface TagService {
    /**
     * find all existing tags
     *
     * @return List of tags if at least one tag was found, otherwise empty List
     * @throws NotFoundException if DataAccessException was caught
     */
    List<TagDTO> findAll() throws NotFoundException;

    /**
     * find tag by given id
     *
     * @param id Long tag id
     * @return instance of TagDTO if tag was found
     * @throws NotFoundException if tag was not found
     */
    TagDTO findById(Long id) throws NotFoundException;

    /**
     * create new tag
     *
     * @param tag TagDTO
     * @throws ModificationException if DataAccessException was caught
     */
    void create(TagDTO tag) throws ModificationException;

    /**
     * delete tag with given id
     *
     * @param id Long tag id
     * @throws NotFoundException  if tag with such id does not exist
     * @throws ModificationException if DataAccessException was caught
     */
    void delete(Long id) throws NotFoundException, ModificationException;
}
