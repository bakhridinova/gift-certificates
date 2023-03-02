--drop tables--
drop table if exists certificate_tag;
drop table if exists certificate;
drop table if exists tag;

-- certificate table--
create table certificate
(
    id                   integer primary key generated always as identity,
    name                 varchar not null,
    description          varchar not null,

    price                double precision not null,
    duration             integer not null,

    create_date          timestamp,
    last_update_date     timestamp
);

--tag table--
create table tag
(
    id                  integer primary key generated always as identity,
    name                varchar unique not null
);

--certificate tag--
--join table for certificate and tag tables--
create table certificate_tag (
    certificate_id      integer not null references certificate(id) on update cascade on delete cascade,
    tag_id              integer not null references tag(id) on update cascade on delete cascade
);