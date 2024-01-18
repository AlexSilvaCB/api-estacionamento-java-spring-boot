insert into USERS(id, username, password, role) values (100, 'bil@gmail.com', '$2a$12$lDMbi7TvQwXha8S8/g3oYut773tWIuBM57/OeO2BiznQzkpEvix06', 'ROLE_CLIENT');
insert into USERS(id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$lDMbi7TvQwXha8S8/g3oYut773tWIuBM57/OeO2BiznQzkpEvix06', 'ROLE_ADMIN');
insert into USERS(id, username, password, role) values (102, 'bob@gmail.com', '$2a$12$lDMbi7TvQwXha8S8/g3oYut773tWIuBM57/OeO2BiznQzkpEvix06', 'ROLE_CLIENT');
insert into USERS(id, username, password, role) values (103, 'toby@gmail.com', '$2a$12$lDMbi7TvQwXha8S8/g3oYut773tWIuBM57/OeO2BiznQzkpEvix06', 'ROLE_CLIENT');

insert into CUSTOMERS(id, name, cpf, id_user) values (10, 'Bianca Silva', '79074426050', 101);
insert into CUSTOMERS(id, name, cpf, id_user) values (20, 'Roberto Gomes', '55352517047', 102);
