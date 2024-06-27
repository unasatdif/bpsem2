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

/*
* tables & views creation to create:
* 1. tables:
*    - team
*    - person
*    - ict_vaardigheid
*    - contact_info
* 2. views:
*    - overzicht
*/
#team table 
drop table if exists team;
create table team(
id int primary key auto_increment,
naam varchar(50) unique not null
);

#person table
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

#ict_vaardigheid table
drop table if exists ict_vaardigheid; 
create table ict_vaardigheid(
id int primary key auto_increment, 
omschrijving varchar(30) not null,
student_id int,
constraint ict_vaardigheid_fk_1
	foreign key(student_id) references person(student_id)
);

#contact_info table 
drop table if exists contact_info;
create table contact_info(
contact_id int primary key,
email varchar(50) unique not null, 
telefoon_nummer varchar(30),
mobiel_nummer varchar(30) not null,
adres varchar(50) not null,
constraint contact_info_fk_1
	foreign key(contact_id) references person(student_id)
);

# overzicht view
create or replace view overzicht as
select 
a.student_id,
a.voornaam, 
a.achternaam, 
a.geboorte_datum, 
a.leeftijd, 
b.id team_id, 
b.naam team, 
c.omschrijving 'ict_vaardigheid',
d.email,
d.telefoon_nummer 'telefoonnummer', 
d.mobiel_nummer 'mobielnummer', 
d.adres
from person a
left join team b 
on a.team_id = b.id
left join ict_vaardigheid c 
on a.student_id = c.student_id
left join contact_info d
on a.student_id = d.contact_id;

# import table data

# team table
insert into team(naam) 
values
('WickedCoders'),
('JavaBasies'),
('Spiderwebs'),
('RTMCoders'),
('GotGit');

# person table
insert into person(voornaam,achternaam,geboorte_datum,leeftijd,team_id)
values
('Yoshka','van der Klift','2001-03-02',23,1),
('Mohammed','Obdam','2006-06-02',18,2),
('Erdal','Sips','2003-09-29',20,3),
('Adrie','de Weert','2007-11-16',16,4),
('Tahira','van der Wal','2006-06-29',17,5),
('Ethan','Ris','2004-11-03',19,1),
('Symon','Buitenhuis','2000-08-22',23,2),
('Hieke','Anker','2001-07-06',22,3),
('Georgio','Xhofleer','2007-01-16',17,4),
('Nikolaas','van Ommeren','2004-09-29',19,5),
('Feyza','Heida','2002-05-08',22,1),
('Lysan','de Veth','2006-02-06',18,2),
('Jethro','Glas','2001-12-17',22,3),
('Rajeev','Hogeweg','1999-05-03',25,4),
('Dyami','Hogenberg','2001-03-27',23,5),
('Neville','Kosten','2005-10-31',18,1),
('Milica','Baart','1999-07-31',24,2),
('Mesut','Demandt','1999-07-04',24,3),
('Sira','de Been','2008-02-12',16,4),
('Yosef','Bosmans','2002-10-13',21,5),
('Melinda','Alferink','2002-01-09',22,1),
('Jaden','de Goede','1998-12-09',25,2),
('Joffrey','Mekking','2000-05-15',24,3),
('Soumaya','Meeuwesen','2006-02-08',18,4),
('Loeke','Helmus','2007-02-08',17,5),
('Cedric','van Lopik','2002-02-25',22, null),
('Arendina','Ballast','2005-09-12',18, null),
('Wilbert','Valk','1998-12-25',25, null),
('Jerom','Wielenga','2001-08-13',25, null),
('Yvonne','Heutinck','2001-08-13',22, null),
('Gerardus','Wijnja','2002-11-27',21, null),
('Abdou','Hölzken','2004-07-13',19, null),
('Frederique','Vaandrager','2004-07-15',19, null),
('Corry','Gillissen','2004-02-24',20, null),
('Romek','Botermans','2004-05-05',20, null),
('Shai','Vlaar','2004-06-06',20, null),
('Zahira','Banga','2007-07-07',16, null),
('Sebahat','Heins','1998-08-15',25, null),
('Ming','van de Weerdhof','2002-01-17',22, null),
('Matt','Goosens','1999-02-26',25, null),
('Oktay','van Gorkom','2004-04-14',20, null),
('Haydar','Hannema','2005-10-07',18, null),
('Mateusz','Annink','2006-03-16',18, null),
('Wladimir','Papen','2005-11-12',18, null),
('Lamar','Twisk','2001-02-15',23, null),
('Nicolien','Veldmeijer','2007-12-15',16, null),
('Ayyoub','Oosthoek','1999-10-10',24, null),
('Guilliano','Gouda','2003-02-23',21, null),
('Casper','Peulen','2003-03-24',21, null),
('Jiska','Bark','2000-07-23',23, null);

# ict_vaardigheid table
insert into ict_vaardigheid(omschrijving,student_id)
values
('Data modeleren',1),
('Networking',2),
('Robotics',3),
('Business Intelligence',16),
('Data modeleren',25),
('Java','4'),
('Database','5');

# contact_info table 
insert into contact_info(contact_id,email,mobiel_nummer,adres)
values
(1,'yoshkavanderklift@armyspy.com','06-55597201','Utenbroekestraat 148'),
(2,'mohammedobdam@superrito.com','06-37177116','Johannes Brinkostraat 115'),
(3,'erdalsips@gustr.com','06-33455363','Mauritspark 160'),
(4,'adriedeweert@jourrapide.com','06-47022249','Paul Krugerstraat 34'),
(5,'tahiravanderwal@jourrapide.com','06-94175952','Zeepkruidlaan 186'),
(6,'ethanris@einrot.com','06-96020215','Veerstoep 98'),
(7,'symonbuitenhuis@fleckens.hu','06-39365320','Zwaluwenweg 20'),
(8,'hiekeanker@fleckens.hu','06-10396829','Voorhof 83'),
(9,'georgioxhofleer@superrito.com','06-66870586','Thorbeckelaan 112'),
(10,'nikolaasvanommeren@armyspy.com','06-58159354','Klompestraat 133'),
(11,'feyzaheida@cuvox.de','06-62033981','Kuiterswegeling 138'),
(12,'lysandeveth@fleckens.hu','06-19738609','Korenbloemstraat 66'),
(13,'jethroglas@armyspy.com','06-92327062','Joan Beukerweg 181'),
(14,'rajeevhogeweg@teleworm.us','06-91237559','Weverskwartier 54'),
(15,'dyamihogenberg@superrito.com','06-75143522','Strevelsweg 45'),
(16,'nevillekosten@rhyta.com','06-72812128','Setheweg 147'),
(17,'milicabaart@fleckens.hu','06-60728906','Sirrahstraat 3'),
(18,'mesutdemandt@jourrapide.com','06-64176322','Paardenbroek 46'),
(19,'siradebeen@jourrapide.com','06-88459096','Ravelstraat 50'),
(20,'yosefbosmans@cuvox.de','06-53161820','Dotterbloem 182'),
(21,'melindaalferink@einrot.com','06-95004166','Weegbree 146'),
(22,'jadendegoede@superrito.com','06-38268393','Kemenadehoek 53'),
(23,'joffreymekking@teleworm.us','06-35571357','Antilopespoor 105'),
(24,'soumayameeuwesen@cuvox.de','06-51385500','Burgemeester Velthuijsenlaan 24'),
(25,'loekehelmus@superrito.com','06-10857176','Lavoisierstraat 137'),
(26,'cedricvanlopik@dayrep.com','06-76458039','De Vloot 146'),
(27,'arendinaballast@dayrep.com','06-45548242','Meteoriet 14'),
(28,'wilbertvalk@superrito.com','06-31813521','Hopakker 39'),
(29,'jeromwielenga@fleckens.hu','06-56679700','Van Coothstraat 155'),
(30,'yvonneheutinck@fleckens.hu','06-33015519','Elsenburglaan 88'),
(31,'gerarduswijnja@einrot.com','06-50072688','Sint Barbaraweg 164'),
(32,'abdouholzken@superrito.com','06-45168110','Noorderhavenkade 115'),
(33,'frederiquevaandrager@gustr.com','06-52824964','Verbindingsweg 192'),
(34,'corrygillissen@rhyta.com','06-44098538','Kortenaerplein 22'),
(35,'romekbotermans@fleckens.hu','06-20725446','Bachstraat 140'),
(36,'shaivlaar@rhyta.com','06-47513984','Rodeweg 182'),
(37,'zahirabanga@gustr.com','06-51717111','Duizendblad 172'),
(38,'sebahatheins@superrito.com','06-62529405','Ekingenstraat 32'),
(39,'mingvandeweerdhof@einrot.com','06-82885207','Veenluydenstraat 132'),
(40,'mattgoosens@superrito.com','06-82885207','Paul Gabrielstraat 92'),
(41,'oktayvangorkom@jourrapide.com','06-16752556','Sint Janstraat 152'),
(42,'haydarhannema@armyspy.com','06-16918396','Laan van de Eekharst 176'),
(43,'mateuszannink@armyspy.com','06-94937072','Haarlemmerstraat 198'),
(44,'wladimirpapen@rhyta.com','06-98813673','Hofsingel 98'),
(45,'lamartwisk@rhyta.com','06-24388233','IJsselstraat 72'),
(46,'nicolienveldmeijer@teleworm.us','06-95349976','Levendaal 31'),
(47,'ayyouboosthoek@gustr.com','06-32372729','Oude Larenseweg 121'),
(48,'guillianogouda@jourrapide.com','06-84875936','Oude Singel 200'),
(49,'casperpeulen@rhyta.com','06-18289444','Ansekerke 23'),
(50,'jiskabark@cuvox.de','06-74811748','Deventerweg 134');

/*
* Queries for retrievel of data 
* Note substitute '?' symbol with appropriate data. '?' symbol is only used for 
* java prepared statement. 
*
* 1. Query aantal registraties
* select count(student_id) 'aantal registraties' 
* from overzicht; 
*
* 2. Query aantal registranten in een team
* select count(student_id) 
* from overzicht 
* where team is null; 
*
* 3. Query aantal registranten niet in een team
* select count(student_id) 
* from overzicht 
* where team is not null; 
*
* 4. Query totaal aantal teams
* select count(id) from team;
*
* 5. Query aantal groepsleden per team
* select team_id, team, count(student_id) 'aantal leden'
* from overzicht
* where team is not null
* group by team
* order by team_id;
*
* 6. Query namenlijst van teamleden in een specifieke team inclusief ict vaardigheid en adres
* select team,concat(voornaam,' ',achternaam) naam, adres, ict_vaardigheid 'favoriete persoonlijke ict vaardigheid'
* from overzicht
* where team_id = ?; #substitute team name for ?
*
* 7. Queries voor nieuwe registratie zonder team
* insert into person
* values
* (?,?,?,?,?,?);
*
* insert into ict_vaardigheid(omschrijving,student_id)
* values
* (?,?);
*
* insert into contact_info
* values
* (?????);
*
*
* 8. Queries voor nieuwe registratie inclusief team
* insert into team(naam) 
* values ('?'); --
*
* insert into person
* values
* (?,?,?,?,?,?);
*
* insert into ict_vaardigheid
* values
* (?,?);
*
* insert into contact_info
* values
* (?????);
*
* 9. Query voor het achterhalen van de laatst aangemaakte team 
* select max(id) from team;
*
* 10. Query voor een uitdraai van alle registranten 
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht; 
*
* 11. Zoek registratent op basis van naam 
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where voornaam like ?; example '%sef'
*
* 12. Zoek registrant op basis van email
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where email like ?; example '%sef'
*
* 13. Zoek registrant op mobielnummer
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where mobielnummer like ?; example '%1820'
*  
* 14. Zoek registrant op geboorte datum 
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where geboorte_datum like '%05'; ?;   example '%2000'
*
* of
*
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where geboorte_datum = ? ;   example '2007-01-16'
*
* 15. Zoek registrant op adres
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where naam like ?; example '%bloem'
*
* 16. Zoek registrant op leeftijd
* select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer
* from overzicht
* where leeftijd like ?; 
*
*
*/