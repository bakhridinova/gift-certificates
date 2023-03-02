package com.epam.esm.entity.filter.sort;

public enum SortType {
    NAME("name"),
    CREATE_DATE("create_date"),
    LAST_UPDATE_DATE("last_update_date");

    private final String stringValue;

    SortType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
