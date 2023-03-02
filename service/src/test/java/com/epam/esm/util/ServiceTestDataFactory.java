package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchPlace;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.entity.filter.sort.SortOrder;
import com.epam.esm.entity.filter.sort.SortType;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * utility class holding static entities for tests
 */

@UtilityClass
public class ServiceTestDataFactory {
    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2020, 2, 20, 10, 10, 10);

    // entity

    public static Tag createTag() {
        return Tag
                .builder()
                .id(0L)
                .name("name")
                .build();
    }

    public static Certificate createCertificate() {
        return Certificate
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price(0.0)
                .duration(0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .tags(Set.of(createTag()))
                .build();
    }

    public static SortFilter createSortFilter() {
        return SortFilter
                .builder()
                .sortType(SortType.NAME)
                .sortOrder(SortOrder.ASC)
                .build();
    }

    public static SearchFilter createSearchFilter() {
        return SearchFilter
                .builder()
                .searchValue("name")
                .searchType(SearchType.NAME)
                .searchPlace(SearchPlace.BEGINS_WITH)
                .build();
    }


    // entity dto

    public static TagDTO createTagDTO() {
        return TagDTO
                .builder()
                .id(0L)
                .name("name")
                .build();
    }

    public static CertificateDTO createCertificateDTO() {
        return  CertificateDTO
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

    public static SortFilterDTO createSortFilterDTO() {
        return SortFilterDTO
                .builder()
                .sortType(SortType.NAME.toString())
                .sortOrder(SortOrder.ASC.toString())
                .build();
    }

    public static SearchFilterDTO createSearchFilterDTO() {
        return SearchFilterDTO
                .builder()
                .searchValue("name")
                .searchType(SearchType.NAME.toString())
                .searchPlace(SearchPlace.BEGINS_WITH.toString())
                .build();
    }


    // null entity

    public static Tag createNullTag() {
        return Tag
                .builder()
                .build();
    }

    public static Certificate createNullCertificate() {
        return Certificate
                .builder()
                .build();
    }

    public static SortFilter createNullSortFilter() {
        return SortFilter
                .builder()
                .build();
    }

    public static SearchFilter createNullSearchFilter() {
        return SearchFilter
                .builder()
                .build();
    }


    // null entity dto

    public static TagDTO createNullTagDTO() {
        return TagDTO
                .builder()
                .build();
    }

    public static CertificateDTO createNullCertificateDTO() {
        return  CertificateDTO
                .builder()
                .build();

    }

    public static SortFilterDTO createNullSortFilterDTO() {
        return SortFilterDTO
                .builder()
                .build();

    }

    public static SearchFilterDTO createNullSearchFilterDTO() {
        return SearchFilterDTO
                .builder()
                .build();
    }
}
