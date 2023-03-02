package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.util.ServiceTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
class FilterMapperTest {
    private final FilterMapper filterMapper;

    @Autowired
    public FilterMapperTest(FilterMapper filterMapper) {
        this.filterMapper = filterMapper;
    }

    @Test
    void shouldMapFiltersCorrectlyTest() {
        SortFilter sortFilter =
                ServiceTestDataFactory.createSortFilter();
        SortFilterDTO sortFilterDTO =
                ServiceTestDataFactory.createSortFilterDTO();

        assertEquals(sortFilter,
                filterMapper.toSortFilter(sortFilterDTO));

        SearchFilter searchFilter =
                ServiceTestDataFactory.createSearchFilter();
        SearchFilterDTO searchFilterDTO =
                ServiceTestDataFactory.createSearchFilterDTO();

        assertEquals(searchFilter,
                filterMapper.toSearchFilter(searchFilterDTO));
    }

    @Test
    void shouldReturnNullIfNullPassedTest() {
        assertNull(filterMapper
                .toSortFilter(null));
        assertNull(filterMapper
                .toSearchFilter(null));
    }

    @Test
    void shouldReturnNullObjectIfNullObjectPassedTest() {
        SortFilter nullSortFilter =
                ServiceTestDataFactory.createNullSortFilter();
        SortFilterDTO nullSortFilterDTO =
                ServiceTestDataFactory.createNullSortFilterDTO();

        assertEquals(nullSortFilter,
                filterMapper.toSortFilter(nullSortFilterDTO));

        SearchFilter nullSearchFilter =
                ServiceTestDataFactory.createNullSearchFilter();
        SearchFilterDTO nullSearchFilterDTO =
                ServiceTestDataFactory.createNullSearchFilterDTO();

        assertEquals(nullSearchFilter,
                filterMapper.toSearchFilter(nullSearchFilterDTO));
    }
}