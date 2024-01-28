insert into USERS (id, username, password, role)
    values (100, 'ana@email.com.br', '$2a$10$AtWo422MdyRQ1RgPzmJNnuDB7xN0GW38sXT4rnBFBqGnMyVmVEf4O', 'ROLE_ADMIN');
insert into USERS (id, username, password, role)
    values (101, 'bia@email.com.br', '$2a$10$AtWo422MdyRQ1RgPzmJNnuDB7xN0GW38sXT4rnBFBqGnMyVmVEf4O', 'ROLE_CLIENT');
insert into USERS (id, username, password, role)
    values (102, 'bob@email.com.br', '$2a$10$AtWo422MdyRQ1RgPzmJNnuDB7xN0GW38sXT4rnBFBqGnMyVmVEf4O', 'ROLE_CLIENT');

insert into CUSTOMERS (id, name, cpf, id_user) values (21, 'Biatriz Rodrigues', '09191773016', 101);
insert into CUSTOMERS (id, name, cpf, id_user) values (22, 'Rodrigo Silva', '98401203015', 102);

insert into vacancy (id, code, status) values (100, 'A-01', 'OCUPADA');
insert into vacancy (id, code, status) values (200, 'A-02', 'OCUPADA');
insert into vacancy (id, code, status) values (300, 'A-03', 'OCUPADA');
insert into vacancy (id, code, status) values (400, 'A-04', 'OCUPADA');
insert into vacancy (id, code, status) values (500, 'A-05', 'OCUPADA');

insert into customer_has_vacancies (receipt_number, plate, brand, model, color, entry_data, id_client, id_vacancy)
    values ('20230313-101300', 'FIT-1010', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 22, 100);
insert into customer_has_vacancies (receipt_number, plate, brand, model, color, entry_data, id_client, id_vacancy)
    values ('20230314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2023-03-14 10:15:00', 21, 200);
insert into customer_has_vacancies (receipt_number, plate, brand, model, color, entry_data, id_client, id_vacancy)
    values ('20230315-101500', 'FIT-1030', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:15:00', 22, 300);
insert into customer_has_vacancies (receipt_number, plate, brand, model, color, entry_data, id_client, id_vacancy)
    values ('20230316-101600', 'SIE-1040', 'FIAT', 'SIENA', 'VERDE', '2023-03-14 10:15:00', 21, 400);
insert into customer_has_vacancies (receipt_number, plate, brand, model, color, entry_data, id_client, id_vacancy)
    values ('20230317-101700', 'SIE-1050', 'FIAT', 'SIENA', 'VERDE', '2023-03-14 10:15:00', 22, 500);