package com.epam.esm.validator;

import com.epam.esm.exception.InvalidRequestBodyException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * utility class for validating request body
 */
@Slf4j
@UtilityClass
public class RequestBodyValidator {
    /**
     * collect field error messages if there are any into single List
     * and throw an exception, otherwise do nothing
     *
     * @param bindingResult BindingResult containing set of violations
     * @throws InvalidRequestBodyException if there's at least one invalid field
     */
    public static void validate(BindingResult bindingResult) throws InvalidRequestBodyException {
        log.info("********** validating request body...");
        if (bindingResult.hasErrors()) {
            List<String> violations = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(fieldError
                    -> violations.add(fieldError.getDefaultMessage()));

            log.error("********** request body validation failed, found " +
                    violations.size() + (violations.size() == 1 ? " violation" : " violations"));
            throw new InvalidRequestBodyException(violations.toArray());
        }
        log.info("********** request body validation succeeded");
    }
}
