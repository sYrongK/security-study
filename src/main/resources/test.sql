-- ddl-auto랑 둘 다 쓸 순 없다고?

drop table member;
drop table authority;

create table member(
    idx int auto_increment primary ,
    id VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL
)
;

create table authority (
    idx int auto_increment primary ,
    member_idx int NOT NULL,
    authority VARCHAR(20) NOT NULL
)
;

INSERT INTO member (id, password)
values ('admin', 'admin')
;
INSERT INTO member (id, password)
values ('user1', 'user')
;

INSERT INTO authority (member_idx, authority)
values (1, 'admin')
;
INSERT INTO authority (member_idx, authority)
values (1, 'USER')
;
INSERT INTO authority (member_idx, authority)
values (2, 'USER')
;