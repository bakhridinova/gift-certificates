package com.epam.esm.util.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.util.DataBaseInfo.TAG_TABLE_PK_COLUMN;
import static com.epam.esm.util.DataBaseInfo.TAG_TABLE_NAME_COLUMN;

/**
 * class for mapping rows of tag result set into tag
 */

@Component
public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag
                .builder()
                .id(rs.getLong(TAG_TABLE_PK_COLUMN.getName()))
                .name(rs.getString(TAG_TABLE_NAME_COLUMN.getName()))
                .build();
    }
}
