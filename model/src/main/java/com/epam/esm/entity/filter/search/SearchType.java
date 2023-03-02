package com.epam.esm.entity.filter.search;

public enum SearchType {
    DESCRIPTION("description"),
    NAME("name");

    private final String stringValue;

    SearchType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
