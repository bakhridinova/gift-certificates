package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.util.ServiceTestEntityHolder.*;
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
    public void shouldMapFiltersCorrectlyTest() {
        assertEquals(sortFilter, filterMapper.toSortFilter(sortFilterDTO));
        assertEquals(searchFilter, filterMapper.toSearchFilter(searchFilterDTO));
    }

    @Test
    public void shouldReturnNullIfNullPassedTest() {
        assertNull(filterMapper.toSortFilter(null));
        assertNull(filterMapper.toSearchFilter(null));
    }

    @Test
    public void shouldReturnNullObjectIfNullObjectPassedTest() {
        assertEquals(nullSortFilter, filterMapper.toSortFilter(nullSortFilterDTO));
        assertEquals(nullSearchFilter, filterMapper.toSearchFilter(nullSearchFilterDTO));
    }
}