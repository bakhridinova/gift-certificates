package com.epam.esm.util.mapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * mapper to convert certificate into certificate dto and vice versa
 */

@org.mapstruct.Mapper(componentModel = "spring", uses = {CertificateServiceImpl.class})
public interface CertificateMapper {
    @Mapping(target = "createDate", expression = "java(dateToString(certificate.getCreateDate()))")
    @Mapping(target = "lastUpdateDate", expression = "java(dateToString(certificate.getLastUpdateDate()))")
    CertificateDTO toCertificateDTO(Certificate certificate);

    @Mapping(target = "createDate", expression = "java(stringToDate(certificateDTO.getCreateDate()))")
    @Mapping(target = "lastUpdateDate", expression = "java(stringToDate(certificateDTO.getLastUpdateDate()))")
    Certificate toCertificate(CertificateDTO certificateDTO);

    default String dateToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    }

    default LocalDateTime stringToDate(String localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ISO_DATE_TIME);
    }
}
