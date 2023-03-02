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
public class ServiceTestEntityHolder {
    // entity
    public static final Tag tag;
    public static final Certificate certificate;
    public static final SortFilter sortFilter;
    public static final SearchFilter searchFilter;

    // entity dto
    public static final TagDTO tagDTO;
    public static final CertificateDTO certificateDTO;
    public static final SortFilterDTO sortFilterDTO;
    public static final SearchFilterDTO searchFilterDTO;

    // null entity
    public static final Tag nullTag;
    public static final Certificate nullCertificate;
    public static final SortFilter nullSortFilter;
    public static final SearchFilter nullSearchFilter;

    // null entity dto
    public static final TagDTO nullTagDTO;
    public static final CertificateDTO nullCertificateDTO;
    public static final SortFilterDTO nullSortFilterDTO;
    public static final SearchFilterDTO nullSearchFilterDTO;

    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2020, 2, 20, 10, 10, 10);

    static {
        tag = Tag
                .builder()
                .id(0L)
                .name("name")
                .build();

        certificate = Certificate
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price(0.0)
                .duration(0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .tags(Set.of(tag))
                .build();

        sortFilter = SortFilter
                .builder()
                .sortType(SortType.NAME)
                .sortOrder(SortOrder.ASC)
                .build();

        searchFilter = SearchFilter
                .builder()
                .searchValue("name")
                .searchType(SearchType.NAME)
                .searchPlace(SearchPlace.BEGINS_WITH)
                .build();



        tagDTO = TagDTO
                .builder()
                .id(0L)
                .name("name")
                .build();

        certificateDTO = CertificateDTO
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price("0.0")
                .duration("0")
                .createDate(localDateTime.toString())
                .lastUpdateDate(localDateTime.toString())
                .tags(new TagDTO[] {tagDTO})
                .build();

        sortFilterDTO = SortFilterDTO
                .builder()
                .sortType(SortType.NAME.toString())
                .sortOrder(SortOrder.ASC.toString())
                .build();

        searchFilterDTO = SearchFilterDTO
                .builder()
                .searchValue("name")
                .searchType(SearchType.NAME.toString())
                .searchPlace(SearchPlace.BEGINS_WITH.toString())
                .build();



        nullTag = Tag
                .builder()
                .build();

        nullCertificate = Certificate
                .builder()
                .build();

        nullSortFilter = SortFilter
                .builder()
                .build();

        nullSearchFilter = SearchFilter
                .builder()
                .build();



        nullTagDTO = TagDTO
                .builder()
                .build();

        nullCertificateDTO = CertificateDTO
                .builder()
                .build();

        nullSortFilterDTO = SortFilterDTO
                .builder()
                .build();

        nullSearchFilterDTO = SearchFilterDTO
                .builder()
                .build();
    }
}
