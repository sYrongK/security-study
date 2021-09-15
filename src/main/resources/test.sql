-- application.yml에서 ddl-auto랑 둘 다 쓸 순 없다고?

INSERT INTO member (username, password)
values ('admin', 'pwd')
;
INSERT INTO member (username, password)
values ('user', 'pwd')
;

INSERT INTO authority (member_idx, authority)
values (1, 'ADMIN')
;
INSERT INTO authority (member_idx, authority)
values (1, 'USER')
;
INSERT INTO authority (member_idx, authority)
values (2, 'USER')
;