package com.epam.esm.util;

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

@UtilityClass
public class RepositoryTestDataFactory {
    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2020, 2, 20, 10, 10, 10);

    public static Tag createTag(String name) {
        return Tag
                .builder()
                .id(0L)
                .name(name)
                .build();
    }

    public static Certificate createCertificate(String name, String description) {
        return Certificate
                .builder()
                .id(0L)
                .name(name)
                .description(description)
                .price(0.0)
                .duration(0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .tags(Set.of(createTag(name)))
                .build();
    }

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

    public static SortFilter createSortFilter(SortType sortType, SortOrder sortOrder) {
        return  SortFilter
                .builder()
                .sortType(sortType)
                .sortOrder(sortOrder)
                .build();

    }

    public static SearchFilter createSearchFilter(String searchValue, SearchType searchType) {
        return SearchFilter
                .builder()
                .searchValue(searchValue)
                .searchType(searchType)
                .searchPlace(SearchPlace.BEGINS_WITH)
                .build();
    }
}
