set foreign_key_checks = 0;
drop table if exists users;
set foreign_key_checks = 1;


CREATE TABLE users (
	id int auto_increment,
	username varchar(255) not null,
	password varchar(255) not null,
	name varchar(255) not null,
	email varchar(255) not null,
	primary key(id)
);

# Test account - password "test"
insert into users(username,password,email,name) VALUES ("test","a94a8fe5ccb19ba61c4c0873d391e987982fbbd3","mezz@dsek.se","Mr Test");
insert into users(username,password,email,name) VALUES ("mezz","3318dc5ce3e4fb7c28a0b841b6801c884e1d0896","lars.gustafson@gmail.com","Lars Gustafson");
