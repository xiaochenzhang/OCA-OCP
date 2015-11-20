-- step 1: create database

CREATE DATABASE designpattern
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Dutch_Netherlands.1252'
       LC_CTYPE = 'Dutch_Netherlands.1252'
       CONNECTION LIMIT = -1;

-- step 2: create sequence
CREATE SEQUENCE games_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 9
  CACHE 1;
ALTER TABLE games_id_seq
  OWNER TO postgres;
 
-- step 3: create table
CREATE TABLE games
(
  id bigint NOT NULL DEFAULT nextval('games_id_seq'::regclass),
  name character varying(255) NOT NULL,
  price character varying(255) NOT NULL,
  review character varying(255),
  CONSTRAINT games_pkey PRIMARY KEY (id),
  CONSTRAINT games_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE games
  OWNER TO postgres;
  
-- step 4: insert dummey data
insert into games (id, name, price, review) values (1,'Dark Souls II', '28', 'Dark Souls II, in the mold of Dark Souls, is a game of noteworthy difficulty');
insert into games (id, name, price, review) values (2,'Dark Souls', '18', 'Dark Souls is a third-person action role-playing game. The core mechanic of the game is exploration');
insert into games (id, name, price, review) values (3,'Hearthstone', '28', 'Hearthstone: Heroes of Warcraft is an online collectible card game');
insert into games (id, name, price, review) values (4,'X-com', '38', 'Xcom computer game');
insert into games (id, name, price, review) values (6,'Mario', '22', 'No review');


  
  