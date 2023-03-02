package com.epam.esm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificateDTO {
    private Long id;

    @NotNull(message = "name should not be null")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 4, max = 40, message = "name must be between 4 and 40 characters")
    private String name;

    @NotNull(message = "description should not be null")
    @NotEmpty(message = "description should not be empty")
    @Size(min = 8, max = 80, message = "description must be between 8 and 80 characters")
    private String description;

    @NotNull(message = "price should not be null")
    @NotEmpty(message = "price should not be empty")
    @Pattern(regexp = "^([1-9]|[12][0-9]|30)\\.[0-9]$", message = "price must be between 1.0 and 30.0")
    private String price;

    @NotNull(message = "duration should not be null")
    @NotEmpty(message = "duration should not be empty")
    @Pattern(regexp = "([1-9]|[1-5][0-9]|60)", message = "duration must be between 1 and 60")
    private String duration;

    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)", message = "date must be in ISO format")
    private String createDate;

    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)", message = "date must be in ISO format")
    private String lastUpdateDate;

    @NotNull(message = "tags should not be null")
    private TagDTO[] tags;
}
