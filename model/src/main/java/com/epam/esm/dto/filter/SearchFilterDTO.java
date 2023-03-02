package com.epam.esm.dto.filter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchFilterDTO {
    @NotNull(message = "search value should not be null")
    @NotEmpty(message = "search value should not be empty")
    @Size(min = 4, max = 80, message = "searchValue must be between 4 and 80 characters")
    private String searchValue;

    @NotNull(message = "search type should not be null")
    @NotEmpty(message = "search type should not be empty")
    @Pattern(regexp = "name|description", message = "searchType must be either name or description")
    private String searchType;

    @NotNull(message = "search place should not be null")
    @NotEmpty(message = "search place should not be empty")
    @Pattern(regexp = "begins_with|contains|ends_with", message = "searchPlace must be either begins_with, contains or ends_with")
    private String searchPlace;
}
