package com.epam.esm.util.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_CREATE_DATE_COLUMN;
import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_DESCRIPTION_COLUMN;
import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_DURATION_COLUMN;
import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_PK_COLUMN;
import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_LAST_UPDATE_DATE_COLUMN;
import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_NAME_COLUMN;
import static com.epam.esm.util.DataBaseInfo.CERTIFICATE_TABLE_PRICE_COLUMN;

/**
 * class for mapping rows of certificate result set into certificate
 */

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate
                .builder()
                .id(rs.getLong(CERTIFICATE_TABLE_PK_COLUMN.getName()))
                .name(rs.getString(CERTIFICATE_TABLE_NAME_COLUMN.getName()))
                .description(rs.getString(CERTIFICATE_TABLE_DESCRIPTION_COLUMN.getName()))
                .price(rs.getDouble(CERTIFICATE_TABLE_PRICE_COLUMN.getName()))
                .duration(rs.getInt(CERTIFICATE_TABLE_DURATION_COLUMN.getName()))
                .createDate(rs.getTimestamp(CERTIFICATE_TABLE_CREATE_DATE_COLUMN.getName()).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(CERTIFICATE_TABLE_LAST_UPDATE_DATE_COLUMN.getName()).toLocalDateTime())
                .build();
    }
}
