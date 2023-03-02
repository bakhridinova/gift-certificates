package com.epam.esm.util;

/**
 * enum for database column names
 */

public enum DataBaseInfo {
    // tag table columns
    TAG_TABLE_PK_COLUMN("id"),
    TAG_TABLE_NAME_COLUMN("name"),

    // certificate table columns
    CERTIFICATE_TABLE_PK_COLUMN("id"),
    CERTIFICATE_TABLE_NAME_COLUMN("name"),
    CERTIFICATE_TABLE_DESCRIPTION_COLUMN("description"),
    CERTIFICATE_TABLE_PRICE_COLUMN("price"),
    CERTIFICATE_TABLE_DURATION_COLUMN("duration"),
    CERTIFICATE_TABLE_CREATE_DATE_COLUMN("create_date"),
    CERTIFICATE_TABLE_LAST_UPDATE_DATE_COLUMN("last_update_date");

    private final String name;

    DataBaseInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
