drop table IF EXISTS customer CASCADE;
create table customer (
	id serial primary key,
	name varchar(200),
	address varchar(200),
	email varchar(100)
);

drop table IF EXISTS container cascade;
create table container(
	id serial primary key,
	description varchar(300)
);

drop table IF EXISTS container_price CASCADE;
create table container_price(
	id serial primary key,
	container_id integer,
	valid_from date,
	valid_to date,
	amount money,
	FOREIGN KEY (container_id) REFERENCES container(id)
);

drop table IF EXISTS panting CASCADE;
create table panting(
	id serial primary key,
	customer_id integer,
	made_at date,
	FOREIGN KEY (customer_id) REFERENCES customer(id)
);

drop table IF EXISTS panting_container CASCADE;
create table panting_container(
	panting_id integer,
	container_id integer,
	number_of_items integer,
	FOREIGN KEY (panting_id) REFERENCES panting(id),
	FOREIGN KEY (container_id) REFERENCES container(id)
);

drop table IF EXISTS invoice CASCADE;
create table invoice (
	id serial primary key,
	customer_id integer,
	issued_at date,
	amount money,
	FOREIGN KEY (customer_id) REFERENCES customer(id)
);
