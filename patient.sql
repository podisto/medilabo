DROP TABLE IF EXISTS patient;

create table if not exists patient(
    id bigint not null auto_increment,
    birth_date date not null,
    address varchar (255),
    first_name varchar (255) not null,
    last_name varchar (255) not null,
    phone_number varchar (255),
    gender enum ('F','M') not null,
    primary key (id)
) engine=InnoDB;


INSERT INTO patient (last_name, first_name, birth_date, gender, address, phone_number)
VALUES ('TestBorderline', 'Test', '19450624', 'M', '2 High St', '200-333-4444');

INSERT INTO patient (last_name, first_name, birth_date, gender, address, phone_number)
VALUES ('TestNone', 'Test', '19661231', 'F', '1 Brookside St', '100-222-3333');

INSERT INTO patient (last_name, first_name, birth_date, gender, address, phone_number)
VALUES ('TestInDanger', 'Test', '20040618', 'M', '3 Club Road', '300-444-5555');

INSERT INTO patient (last_name, first_name, birth_date, gender, address, phone_number)
VALUES ('TestEarlyOnset', 'Test', '20020628', 'F', '4 Valley Dr', '400-555-6666');
