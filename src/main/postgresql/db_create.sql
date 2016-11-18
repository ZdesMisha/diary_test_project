/* Database Schema */

CREATE DATABASE diary;
CREATE USER diary_user WITH PASSWORD 'qwerty';
GRANT ALL PRIVILEGES ON DATABASE diary TO diary_user;


CREATE SEQUENCE products_id_seq;

CREATE TABLE products (
  id      INT NOT NULL DEFAULT nextval('products_id_seq') PRIMARY KEY,
  code    INT NOT NULL,
  name    TEXT NOT NULL UNIQUE,
  price    FLOAT NOT NULL,
  p_date TIMESTAMP NOT NULL
);