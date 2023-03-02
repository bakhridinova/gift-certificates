package com.epam.esm.entity.filter.sort;

public enum SortOrder {
    ASC("asc"),
    DESC("desc");

    private final String stringValue;

    SortOrder(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
