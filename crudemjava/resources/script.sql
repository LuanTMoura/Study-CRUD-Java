create database contactlist;

create table contact (
id int not null auto_increment primary key,
name varchar (40),
age int,
registerDate date
);

describe contact;

select * from contact;