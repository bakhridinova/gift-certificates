package com.epam.esm.entity.filter.search;

public enum SearchPlace {
    BEGINS_WITH("' %'"),
    CONTAINS("'% %'"),
    ENDS_WITH("' x'");
    private final String stringValue;

    SearchPlace(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue(String searchValue) {
        return stringValue.replace(" ", searchValue);
    }
}
