create table customer (
	id integer primary key,
	address varchar(200),
	email varchar(100)
);

create table container(
	id integer primary key,
	description varchar(300)
);

create table container_price(
	id integer primary key,
	container_id integer,
	valid_from date,
	valid_to date,
	amount money
);

create table panting(
	id integer primary key,
	customer_id integer,
	made_at date
);

create table panting_containers(
	panting_id integer,
	container_id integer,
	number_of_items integer
);

create table invoice (
	id integer primary key,
	customer_id integer,
	issued_at date,
	amount money
);
