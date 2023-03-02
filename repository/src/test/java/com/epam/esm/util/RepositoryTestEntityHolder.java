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
public class RepositoryTestEntityHolder {
    public static final Tag tag;
    public static final Certificate certificate;
    public static final SortFilter sortFilter;
    public static final SearchFilter searchFilter;

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
    }
}
