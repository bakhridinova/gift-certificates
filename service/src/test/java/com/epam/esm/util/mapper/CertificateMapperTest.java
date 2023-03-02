package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
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
class CertificateMapperTest {
    private final CertificateMapper certificateMapper;

    @Autowired
    public CertificateMapperTest(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Test
    void shouldMapCertificatesCorrectlyTest() {
        Certificate certificate =
                ServiceTestDataFactory.createCertificate();
        CertificateDTO certificateDTO =
                ServiceTestDataFactory.createCertificateDTO();

        assertEquals(certificate,
                certificateMapper.toCertificate(certificateDTO));
        assertEquals(certificateDTO,
                certificateMapper.toCertificateDTO(certificate));
    }

    @Test
    void shouldReturnNullIfNullPassedTest() {
        assertNull(certificateMapper
                .toCertificate(null));
        assertNull(certificateMapper
                .toCertificateDTO(null));
    }

    @Test
    void shouldReturnNullObjectIfNullObjectPassedTest() {
        Certificate nullCertificate =
            ServiceTestDataFactory.createNullCertificate();
        CertificateDTO nullCertificateDTO =
                ServiceTestDataFactory.createNullCertificateDTO();


        assertEquals(nullCertificate,
                certificateMapper.toCertificate(nullCertificateDTO));
        assertEquals(nullCertificateDTO,
                certificateMapper.toCertificateDTO(nullCertificate));
    }
}