-- qt_db.sql
drop database qt_db;
drop user qtuser;

create user qtuser with password 'password';
create database qt_db with template=template0 owner=qtuser;

\connect qt_db;

alter default privileges grant all on tables to qtuser;
alter default privileges grant all on sequences to qtuser;

create table qt_users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null
);

create table qt_tasks(
task_id integer primary key not null,
user_id integer not null,
title varchar(20),
start_date bigint not null,
end_date bigint not null,
project varchar(100),
description varchar(50) not null,
priority varchar(50),
file text not null
);

--alter table um_tasks add constraint tas_users_fk
--foreign key (user_id) references um_users(user_id);


create table qt_assignees(
assignee_id integer primary key not null,
task_id integer not null,
user_id integer not null,
task_date bigint not null
);

--alter table um_assignee add constraint ass_tas_fk
--foreign key (task_id) references um_tasks(task_id);
--
--alter table um_assignee add constraint ass_users_fk
--foreign key (user_id) references um_users(user_id);

create sequence qt_users_seq increment 1 start 1;
create sequence qt_tasks_seq increment 1 start 1;
create sequence qt_assignees_seq increment 1 start 1;

