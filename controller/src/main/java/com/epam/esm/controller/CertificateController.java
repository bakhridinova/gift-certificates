package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.exception.ExceptionHandler;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.reponse.ResponseData;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.RequestBodyValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller for processing get/post/patch/delete requests on certificate
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    /**
     * method for processing get request for all certificates
     *
     * @return ResponseData with List of certificateDTO`s
     * @throws NotFoundException if error while finding certificates occurred
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     */
    @GetMapping
    public ResponseData<List<CertificateDTO>> getAll()
            throws NotFoundException {
        log.info("********** processing get request...");
        return new ResponseData<>(certificateService.findAll(), HttpStatus.OK);
    }

    /**
     * method for processing get request for particular certificate with given id
     *
     * @param id long passed as path variable
     * @return ResponseData with certificateDTO
     * @throws NotFoundException if certificate was not found
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     */
    @GetMapping(value = "/{id}")
    public ResponseData<CertificateDTO> getById(@PathVariable long id)
            throws NotFoundException {
        log.info("********** processing get request...");
        return new ResponseData<>(certificateService.findById(id), HttpStatus.OK);
    }

    /**
     * method for processing post request for certificates with given tags
     *
     * @param tag TagDTO passed as request body
     * @param bindingResult BindingResult containing set of violations
     * @return ResponseData with List of certificateDTO`s
     * @throws InvalidRequestBodyException if validation failed
     * @throws NotFoundException if error while finding certificates occurred
     * @see ExceptionHandler#handleInvalidRequestBodyException(InvalidRequestBodyException)
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     * @see RequestBodyValidator#validate(BindingResult)
     */
    @PostMapping(value = "/tag")
    public ResponseData<List<CertificateDTO>> getByTag(@RequestBody @Valid TagDTO tag,
                                                       BindingResult bindingResult)
            throws InvalidRequestBodyException, NotFoundException {
        log.info("********** processing post request...");
        RequestBodyValidator.validate(bindingResult);
        return new ResponseData<>(certificateService.findByTag(tag), HttpStatus.OK);
    }

    /**
     * method for processing post request for certificates with given search parameters
     *
     * @param searchFilter SearchFilterDTO passed as request body
     * @param bindingResult BindingResult containing set of violations
     * @return ResponseData with List of certificateDTO`s
     * @throws InvalidRequestBodyException if validation failed
     * @throws NotFoundException if error while finding certificates occurred
     * @see ExceptionHandler#handleInvalidRequestBodyException(InvalidRequestBodyException)
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     * @see RequestBodyValidator#validate(BindingResult)
     */
    @PostMapping(value = "/search")
    public ResponseData<List<CertificateDTO>> getBySearchFilter(@RequestBody @Valid SearchFilterDTO searchFilter,
                                                                BindingResult bindingResult)
            throws InvalidRequestBodyException, NotFoundException {
        log.info("********** processing post request...");
        RequestBodyValidator.validate(bindingResult);
        return new ResponseData<>(certificateService.findBySearchFilter(searchFilter), HttpStatus.OK);
    }

    /**
     * method for processing post request for all certificates sorted by given sort filters
     * list can contain up to two sort filters, the ones after second will be ignored
     *
     * @param sortFilter SortFilterDTO passed as request body
     * @param bindingResult BindingResult containing set of violations
     * @return ResponseData with List of certificateDTO`s
     * @throws InvalidRequestBodyException if validation failed
     * @throws NotFoundException if error while finding certificates occurred
     * @see ExceptionHandler#handleInvalidRequestBodyException(InvalidRequestBodyException)
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     * @see RequestBodyValidator#validate(BindingResult)
     */
    @PostMapping(value = "/sort")
    public ResponseData<List<CertificateDTO>> getBySortFilter(@RequestBody SortFilterDTO sortFilter,
                                                              BindingResult bindingResult)
            throws InvalidRequestBodyException, NotFoundException {
        log.info("********** processing post request...");
        RequestBodyValidator.validate(bindingResult);
        return new ResponseData<>(certificateService.findBySortFilter(sortFilter), HttpStatus.OK);
    }

    /**
     * method for processing post request for new certificate
     *
     * @param certificate CertificateDTO passed as request body
     * @param bindingResult BindingResult containing set of violations
     * @return ResponseData with success message and ok status
     * @throws InvalidRequestBodyException if validation failed
     * @throws ModificationException if certificate was not created
     * @see ExceptionHandler#handleInvalidRequestBodyException(InvalidRequestBodyException)
     * @see ExceptionHandler#handleModificationException(ModificationException)
     * @see RequestBodyValidator#validate(BindingResult)
     */
    @PostMapping
    public ResponseData<Object> create(@RequestBody @Valid CertificateDTO certificate,
                                       BindingResult bindingResult)
            throws InvalidRequestBodyException, ModificationException {
        log.info("********** processing post request...");
        RequestBodyValidator.validate(bindingResult);
        certificateService.create(certificate);
        log.info("********** certificate was successfully created...");
        return new ResponseData<>("certificate was successfully created!", HttpStatus.OK);
    }

    /**
     * method for processing patch request for editing certificate
     *
     * @param certificate CertificateDTO passed as request body
     * @param bindingResult BindingResult containing set of violations
     * @return ResponseData with success message and ok status
     * @throws InvalidRequestBodyException if validation failed
     * @throws NotFoundException if certificate was not found
     * @throws ModificationException if certificate was not edited
     * @see ExceptionHandler#handleInvalidRequestBodyException(InvalidRequestBodyException)
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     * @see ExceptionHandler#handleModificationException(ModificationException)
     * @see RequestBodyValidator#validate(BindingResult)
     */
    @PatchMapping
    public ResponseData<Object> edit(@RequestBody @Valid CertificateDTO certificate,
                                     BindingResult bindingResult)
            throws InvalidRequestBodyException, NotFoundException, ModificationException {
        log.info("********** processing patch request...");
        RequestBodyValidator.validate(bindingResult);
        certificateService.update(certificate);
        log.info("********** certificate was successfully updated...");
        return new ResponseData<>("certificate was successfully updated!", HttpStatus.OK);
    }

    /**
     * method for processing delete request for particular certificate with given id
     *
     * @param id long passed as path variable
     * @return ResponseData with success message and ok status
     * @throws NotFoundException if certificate was not found
     * @throws ModificationException if certificate was not deleted
     * @see ExceptionHandler#handleNotFoundException(NotFoundException)
     * @see ExceptionHandler#handleModificationException(ModificationException)
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData<Object> deleteById(@PathVariable long id)
            throws NotFoundException, ModificationException {
        log.info("********** processing delete request...");
        certificateService.delete(id);
        log.info("********** certificate was successfully deleted...");
        return new ResponseData<>("certificate was successfully deleted!", HttpStatus.OK);
    }
}
