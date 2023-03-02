package com.epam.esm.util;

import lombok.experimental.UtilityClass;

/**
 * utility class for database column names and queries
 */

@UtilityClass
public class DataBaseInfoAndQueryHolder {
    // tag table columns
    public static final String TAG_TABLE_PK_COLUMN = "id";
    public static final String TAG_TABLE_NAME_COLUMN = "name";

    // certificate table columns
    public static final String CERTIFICATE_TABLE_PK_COLUMN = "id";
    public static final String CERTIFICATE_TABLE_NAME_COLUMN = "name";
    public static final String CERTIFICATE_TABLE_DESCRIPTION_COLUMN = "description";
    public static final String CERTIFICATE_TABLE_PRICE_COLUMN = "price";
    public static final String CERTIFICATE_TABLE_DURATION_COLUMN = "duration";
    public static final String CERTIFICATE_TABLE_CREATE_DATE_COLUMN = "create_date";
    public static final String CERTIFICATE_TABLE_LAST_UPDATE_DATE_COLUMN = "last_update_date";


    // tag table queries
    public static final String FIND_ALL_TAGS = "select * from tag;";
    public static final String FIND_TAG_BY_ID = "select * from tag where id = ?;";
    public static final String INSERT_TAG = "insert into tag (name) values (?) on conflict do nothing returning id;";
    public static final String DELETE_TAG = "delete from tag where id = ?;";

    // certificate table queries
    public static final String FIND_ALL_CERTIFICATES = "select * from certificate;";
    public static final String FIND_CERTIFICATE_BY_ID = "select * from certificate where id = ?;";
    public static final String FIND_CERTIFICATES_BY_TYPE = "select * from certificate where %s like %s;";
    public static final String FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE = "select * from certificate order by %s %s;";
    public static final String UPDATE_CERTIFICATE = "update certificate set name = coalesce(?, name), description = coalesce(?, description), price = coalesce(?, price), duration = coalesce(?, duration), last_update_date = now() where id = ?;";
    public static final String INSERT_CERTIFICATE = "insert into certificate (name, description, price, duration, create_date, last_update_date) values (?, ?, ?, ?, now(), now()) on conflict do nothing returning id;";
    public static final String DELETE_CERTIFICATE = "delete from certificate where id = ?;";

    // certificate_tag table queries
    public static final String INSERT_TAG_TO_CERTIFICATE = "insert into certificate_tag (certificate_id, tag_id) values(?, ?);";
    public static final String DELETE_ALL_CERTIFICATE_TAGS = "delete from certificate_tag where certificate_id = ?;";
    public static final String FIND_ALL_CERTIFICATE_TAGS = "select * from certificate_tag inner join tag t on t.id = certificate_tag.tag_id where certificate_id = ?";
    public static final String FIND_CERTIFICATES_BY_TAG = "select c.id, c.name, description, price, duration, create_date, last_update_date from certificate c inner join certificate_tag ct on c.id = ct.certificate_id inner join tag t on t.id = ct.tag_id where t.name = ?;";
}
