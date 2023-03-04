package com.epam.esm.util.mapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.mapstruct.Mapper;

/**
 * mapper to convert certificate into certificate dto and vice versa
 */

@Mapper(componentModel = "spring", uses = {CertificateServiceImpl.class})
public interface CertificateMapper {
    CertificateDTO toCertificateDTO(Certificate certificate);

    Certificate toCertificate(CertificateDTO certificateDTO);
}
