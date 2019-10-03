use blogdb;
create table blog
(
    id int auto_increment primary key not null,
    account_id varchar(100),
    name varchar(50),
    content text,
    label varchar(100),
    gmt_create bigint,
    gmt_modified bigint
);