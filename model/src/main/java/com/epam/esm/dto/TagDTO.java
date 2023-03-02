package com.epam.esm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {
    private Long id;

    @NotNull(message = "name should be null")
    @NotEmpty(message = "name should not be empty")
    @Pattern(regexp = "^[A-Za-z]*$", message = "tag name must contain only letters")
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;
}
