package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    @Size(min = 2, max = 20, message = "price must be between 2 and 20 characters")
    private String price;


    @NotNull(message = "duration should not be null")
    @NotEmpty(message = "duration should not be empty")
    @Size(min = 1, max = 60, message = "duration should be between 1 and 60 characters")
    private String duration;

    @JsonFormat(pattern = "yyyy-mm-ddThh:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-mm-ddThh:mm:ss")
    private LocalDateTime lastUpdateDate;

    @NotNull(message = "tags should not be null")
    private TagDTO[] tags;
}
