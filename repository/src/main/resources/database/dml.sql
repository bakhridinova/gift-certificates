insert into certificate (name, description, price, duration, create_date, last_update_date) values ('happy birthday!', 'from good friends and true, from old friends and new, may good luck go with you and happiness too', 10.0, 10, '2023-01-20 03:16:42', '2023-01-24 03:00:43');
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('happy newborn!', 'welcome to the world baby boy, you are gonna be such an amazing person, with your amazing parents <3', 5.0, 30, '2023-01-23 01:48:17', '2023-02-21 05:52:33');
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('happy promotion!', 'congratulations on your well-deserved promotion! hope this brings you all kinds of new challenges and opportunities', 25.0, 20, '2023-02-04 06:04:31', '2023-02-15 09:33:01');
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('happy vacation!', 'may you enjoy your holiday to the fullest! will be waiting to hear amazing stories when you are back', 15.0, 40, '2023-01-02 22:39:16', '2023-01-03 17:56:06');
insert into certificate (name, description, price, duration, create_date, last_update_date) values ('happy anniversary!', 'I am so blessed to be married to someone as wonderful as you. thank you for always loving me for who I am', 20.0, 60, '2023-02-21 05:52:33', '2023-02-25 17:53:52');

insert into tag (name) values ('family');
insert into tag (name) values ('job');
insert into tag (name) values ('travelling');
insert into tag (name) values ('love');
insert into tag (name) values ('birthday');
insert into tag (name) values ('baby');
insert into tag (name) values ('friend');

insert into certificate_tag (certificate_id, tag_id) values (1, 5);
insert into certificate_tag (certificate_id, tag_id) values (5, 4);
insert into certificate_tag (certificate_id, tag_id) values (3, 2);
insert into certificate_tag (certificate_id, tag_id) values (5, 1);
insert into certificate_tag (certificate_id, tag_id) values (2, 6);
insert into certificate_tag (certificate_id, tag_id) values (4, 3);
insert into certificate_tag (certificate_id, tag_id) values (2, 4);