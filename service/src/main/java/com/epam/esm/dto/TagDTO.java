package com.epam.esm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {
    private long id;

    @NotNull(message = "name should be null")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;
}
