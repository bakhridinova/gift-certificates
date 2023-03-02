package com.epam.esm.entity.filter.sort;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortFilter {
    private SortType sortType;
    private SortOrder sortOrder;
}
