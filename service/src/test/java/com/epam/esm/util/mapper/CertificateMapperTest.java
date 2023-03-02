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
class CertificateMapperTest {
    private final CertificateMapper certificateMapper;

    @Autowired
    public CertificateMapperTest(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Test
    public void shouldMapCertificatesCorrectlyTest() {
        assertEquals(certificate, certificateMapper.toCertificate(certificateDTO));
        assertEquals(certificateDTO, certificateMapper.toCertificateDTO(certificate));
    }

    @Test
    public void shouldReturnNullIfNullPassedTest() {
        assertNull(certificateMapper.toCertificate(null));
        assertNull(certificateMapper.toCertificateDTO(null));
    }

    @Test
    public void shouldReturnNullObjectIfNullObjectPassedTest() {
        assertEquals(nullCertificate, certificateMapper.toCertificate(nullCertificateDTO));
        assertEquals(nullCertificateDTO, certificateMapper.toCertificateDTO(nullCertificate));
    }
}