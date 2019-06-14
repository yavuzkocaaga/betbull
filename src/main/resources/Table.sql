DROP SEQUENCE  IF EXISTS betbull.foot_team_seq;
create sequence betbull.foot_team_seq
;
DROP SEQUENCE  IF EXISTS betbull.team_seq;
create sequence betbull.team_seq
;
DROP SEQUENCE  IF EXISTS betbull.footboller_seq;
create sequence betbull.footboller_seq
;

create table if not exists betbull.team
(
	team_id bigint not null
		constraint team_pkey
			primary key,
	created_date timestamp,
	last_modified_date timestamp,
	country_code varchar(5) not null,
	name varchar(65) not null
		constraint uk_g2l9qqsoeuynt4r5ofdt1x2td
			unique,
	status integer
)
;

create table if not exists betbull.footballer
(
	footballer_id bigint not null
		constraint footballer_pkey
			primary key
		constraint fk8lcwtq0nm42h39ogmqi4tk2ep
			references team,
	created_date timestamp,
	last_modified_date timestamp,
	age integer not null,
	contract_cost double precision not null,
	date_of_birth date not null,
	experience_month integer not null,
	full_name varchar(100) not null,
	height integer not null,
	nationality varchar(255) not null,
	position varchar(20) not null,
	shirt_number integer not null,
	team_commision double precision not null,
	transfer_cost integer not null,
	weight integer not null
)
;

create table if not exists betbull.footballer_team
(
	footballer_team_id bigint not null
		constraint footballer_team_pkey
			primary key,
	created_date timestamp,
	last_modified_date timestamp,
	contract_end_date date not null,
	contract_start_date date not null,
	footballer_id bigint not null
		
)
;