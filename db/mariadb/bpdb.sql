/* Database creation script with create table statements
*  file location: project_folder->db->mariadb 
*  team members: 
*  Adit Bajnath     | BI/1123/006
*  Dimitri Forster  | BI/1123/013
*  Melvin Geysvliet | BI/1123/014
*  Darryl Lokhai    | BI/1123/020
* Author: Dimitri Forster 
*/

# create database and select databes to run queries
drop database if exists bpdb; 
create database bpdb;
use bpdb;
# create person table
drop table if exists person;
create table person (
student_id int primary key auto_increment,
voornaam varchar(30) not null,
achternaam varchar(30) not null,
geboorte_datum date not null, 
leeftijd int, 
team_id int,
constraint person_fk_1 
	foreign key(team_id) references team(id)
);

# create team table 
drop table if exists team;
create table team(
id int primary key auto_increment,
naam varchar(50)
);
# create ict_vaardigheid table
drop table if exists ict_vaardigheid; 
create table ict_vaardigheid(
id int primary key, 
omschrijving varchar(30) not null,
student_id int,
constraint ict_vaardigheid_fk_1
	foreign key(student_id) references person(student_id)
);
# create contact_info table 
drop table if exists contact_info;
create table contact_info(
contact_id int primary key,
email varchar(50) not null, 
telefoon_nummer varchar(30),
mobiel_nummer varchar(30) not null,
adres varchar(50) not null,
constraint contact_info_fk_1
	foreign key(contact_id) references person(student_id)
);
