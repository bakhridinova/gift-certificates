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

insert into certificate (name, description, price, duration, create_date, last_update_date) values ('name',  'description', 0.0, 0, now(), now());
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('name1', 'description1', 0.0, 0, now(), now());
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('name2', 'description2', 0.0, 0, now(), now());
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('name3', 'description3', 0.0, 0, now(), now());

insert into tag (name) values ('name');
insert into tag (name) values ('name1');
insert into tag (name) values ('name2');
insert into tag (name) values ('name3');

insert into certificate_tag (certificate_id, tag_id) values (1, 1);
insert into certificate_tag (certificate_id, tag_id) values (2, 2);
insert into certificate_tag (certificate_id, tag_id) values (3, 3);
insert into certificate_tag (certificate_id, tag_id) values (4, 4);