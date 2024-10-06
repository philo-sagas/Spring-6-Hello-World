DROP TABLE IF EXISTS Ingredient;
DROP SEQUENCE IF EXISTS Ingredient_Sequence;

CREATE SEQUENCE IF NOT EXISTS Ingredient_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000000
    CACHE 1;

CREATE TABLE IF NOT EXISTS Ingredient
(
    id int primary key default nextval('Ingredient_Sequence'),
    slug varchar(4)  not null,
    name varchar(25) not null,
    type varchar(10) not null
);
