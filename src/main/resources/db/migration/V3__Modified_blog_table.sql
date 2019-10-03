use blogdb;
drop table blog;
create table blog
(
    id int auto_increment primary key not null,
    account_id varchar(100),
    title varchar(50),
    content text,
    tags varchar(100),
    gmt_create bigint,
    gmt_modified bigint,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0
);