package com.epam.esm.util.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.mapstruct.Mapper;

/**
 * mapper to convert tag into tag dto and vice versa
 */

@Mapper(componentModel = "spring", uses = {CertificateServiceImpl.class, TagServiceImpl.class})
public interface TagMapper {
    TagDTO toTagDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}
