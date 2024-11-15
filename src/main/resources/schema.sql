
CREATE DATABASE iF NOT EXISTS gilded;



USE gilded;

CREATE TABLE IF NOT EXISTS grtimestamp (
                                           grdate date
);

CREATE TABLE IF NOT EXISTS inventory (
                                         id int primary key auto_increment,
                                         item_name varchar(50),
                                         start_date date,
                                         sell_in int,
                                         quality int
);

create user gilded_user@localhost  identified by "gilded_pass";



grant all privileges on gilded.* to gilded_user;
