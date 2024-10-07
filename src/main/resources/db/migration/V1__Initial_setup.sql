CREATE TABLE ourusers
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    roles    VARCHAR(255)        NOT NULL
);

create table products
(
    id          serial primary key,
    description varchar(255) not null,
    name        varchar(255) not null
);

INSERT INTO ourusers (id, email, password, roles) VALUES (1, 'admin@a.b', '$2a$10$uRCEmiBtPQ6EsV5PIHTmBuD2jUkxN7rjLzVmaAGgWWPTrB04v9Acu', 'USER,ADMIN');
INSERT INTO ourusers (id, email, password, roles) VALUES (2, 'user@a.b', '$2a$10$izxdCtdqgnlp/Lk.geMGSuPgFsQ4QAliNT1Mgp3/rvh0evDmX13uW', 'USER');

INSERT INTO products (id, description, name) VALUES (1, 'The first product', 'First');


