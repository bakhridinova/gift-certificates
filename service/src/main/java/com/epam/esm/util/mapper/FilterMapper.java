package com.epam.esm.util.mapper;

import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchPlace;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.entity.filter.sort.SortOrder;
import com.epam.esm.entity.filter.sort.SortType;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.mapstruct.Mapping;

/**
 * mapper to convert filter dto into filter
 */

@org.mapstruct.Mapper(componentModel = "spring", uses = CertificateServiceImpl.class)
public interface FilterMapper {
    @Mapping(target = "sortType", expression = "java(toSortType(sortFilterDTO.getSortType()))")
    @Mapping(target = "sortOrder", expression = "java(toSortOrder(sortFilterDTO.getSortOrder()))")
    SortFilter toSortFilter(SortFilterDTO sortFilterDTO);

    @Mapping(target = "searchType", expression = "java(toSearchType(searchFilterDTO.getSearchType()))")
    @Mapping(target = "searchPlace", expression = "java(toSearchPlace(searchFilterDTO.getSearchPlace()))")
    SearchFilter toSearchFilter(SearchFilterDTO searchFilterDTO);

    default SortType toSortType(String sortType) {
        if (sortType == null) {
            return null;
        }
        return SortType.valueOf(sortType.toUpperCase());
    }

    default SortOrder toSortOrder(String sortOrder) {
        if (sortOrder == null) {
            return null;
        }
        return SortOrder.valueOf(sortOrder.toUpperCase());
    }

    default SearchType toSearchType(String searchType) {
        if (searchType == null) {
            return null;
        }
        return SearchType.valueOf(searchType.toUpperCase());
    }

    default SearchPlace toSearchPlace(String searchPlace) {
        if (searchPlace == null) {
            return null;
        }
        return SearchPlace.valueOf(searchPlace.toUpperCase());
    }
}
