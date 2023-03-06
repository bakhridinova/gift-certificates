package com.epam.esm.util;

/**
 * enum for database queries
 */

public enum DataBaseQuery {
    // tag table queries
    FIND_ALL_TAGS("select * from tag;"),
    FIND_TAG_BY_ID("select * from tag where id = ?;"),
    INSERT_TAG("insert into tag (name) values (?) on conflict (name) do update set name = excluded.name returning id;"),
    DELETE_TAG("delete from tag where id = ?;"),

    // certificate table queries
    FIND_ALL_CERTIFICATES("select * from certificate;"),
    FIND_CERTIFICATE_BY_ID("select * from certificate where id = ?;"),
    FIND_CERTIFICATES_BY_TYPE("select * from certificate where %s like %s;"),
    FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE("select * from certificate order by %s %s;"),
    UPDATE_CERTIFICATE("update certificate set name = coalesce(?, name), description = coalesce(?, description), price = coalesce(?, price), duration = coalesce(?, duration), last_update_date = now() where id = ?;"),
    INSERT_CERTIFICATE("insert into certificate (name, description, price, duration, create_date, last_update_date) values (?, ?, ?, ?, now(), now()) on conflict do nothing returning id;"),
    DELETE_CERTIFICATE("delete from certificate where id = ?;"),

    // certificate_tag table queries
    INSERT_TAG_TO_CERTIFICATE("insert into certificate_tag (certificate_id, tag_id) values(?, ?);"),
    DELETE_ALL_CERTIFICATE_TAGS("delete from certificate_tag where certificate_id = ?;"),
    FIND_ALL_CERTIFICATE_TAGS("select * from certificate_tag inner join tag t on t.id = certificate_tag.tag_id where certificate_id = ?;"),
    FIND_CERTIFICATES_BY_TAG("select c.id, c.name, description, price, duration, create_date, last_update_date from certificate c inner join certificate_tag ct on c.id = ct.certificate_id inner join tag t on t.id = ct.tag_id where t.name = ?;");

    private final String query;

    DataBaseQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
