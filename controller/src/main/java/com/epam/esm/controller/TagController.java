package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ExceptionHandler;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.reponse.ResponseData;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.RequestBodyValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller for processing get/post/delete requests on tag
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    /**
     * method for processing get request for all tags
     *
     * @return ResponseData with List of tagDTO`s
     * @throws NotFoundException if error while finding tags occurred
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     */
    @GetMapping
    public ResponseData<List<TagDTO>> getAll()
            throws NotFoundException {
        log.info("********** processing get request...");
        return new ResponseData<>(tagService.findAll(), HttpStatus.OK);
    }

    /**
     * method for processing get request for particular tag with given id
     *
     * @param id long passed as path variable
     * @return ResponseData with tagDTO
     * @throws NotFoundException if tag was not found
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     */
    @GetMapping(value = "/{id}")
    public ResponseData<TagDTO> getById(@PathVariable long id)
            throws NotFoundException {
        log.info("********** processing get request...");
        return new ResponseData<>(tagService.findById(id), HttpStatus.OK);
    }

    /**
     * method for processing post request for new tag
     *
     * @param tags tags passed as request body
     * @param bindingResult BindingResult containing set of violations
     * @return ResponseData with success message and ok status
     * @throws InvalidRequestBodyException if validation failed
     * @throws ModificationException if tag was not created
     * @see ExceptionHandler#handleInvalidRequestBodyException(InvalidRequestBodyException)
     * @see ExceptionHandler#handleModificationException(ModificationException)
     * @see RequestBodyValidator#validate(BindingResult)
     */
    @PostMapping
    public ResponseData<Object> create(@RequestBody @Valid TagDTO tags,
                                       BindingResult bindingResult)
            throws InvalidRequestBodyException, ModificationException {
        log.info("********** processing post request...");
        RequestBodyValidator.validate(bindingResult);
        tagService.create(tags);
        log.info("********** tag was successfully created...");
        return new ResponseData<>("tag was successfully created!", HttpStatus.OK);
    }

    /**
     * method for processing delete request for particular tag with given id
     *
     * @param id long passed as path variable
     * @return ResponseData with success message and ok status
     * @throws NotFoundException if tag was not found
     * @throws ModificationException if tag was not deleted
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     * @see ExceptionHandler#handleModificationException(ModificationException)
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData<Object> deleteById(@PathVariable long id)
            throws NotFoundException, ModificationException {
        log.info("********** processing delete request...");
        tagService.delete(id);
        log.info("********** tag was successfully deleted...");
        return new ResponseData<>("tag was successfully deleted!", HttpStatus.OK);
    }
}
