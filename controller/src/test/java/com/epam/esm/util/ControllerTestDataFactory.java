package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ControllerTestDataFactory {
    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2020, 2, 20, 10, 10, 10);

    public static TagDTO createTagDTO() {
        return TagDTO
                .builder()
                .id(0L)
                .name("name")
                .build();
    }

    public static CertificateDTO createCertificateDTO() {
        return CertificateDTO
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price("0.0")
                .duration("0")
                .createDate(String.valueOf(localDateTime))
                .lastUpdateDate(String.valueOf(localDateTime))
                .tags(new TagDTO[] {createTagDTO()})
                .build();
    }

    public static SortFilterDTO createInvalidSortFilterDTO() {
        return SortFilterDTO
                .builder()
                .sortType("type")
                .sortOrder("order")
                .build();
    }

    public static SearchFilterDTO createInvalidSearchFilterDTO() {
        return SearchFilterDTO
                .builder()
                .searchValue("value")
                .searchType("type")
                .searchPlace("place")
                .build();
    }
}
