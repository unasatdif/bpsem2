CREATE DATABASE Beroepsproduct_semester_2_blok_1;
USE Beroepsproduct_semester_2_blok_1;
-- Creating the tables with the assigned contraints following from the ERD --
-- Create table Persoon--
CREATE TABLE Persoon(
Studenten_nummer INT PRIMARY KEY,
Voornaam VARCHAR(75) NOT NULL,
Achternaam VARCHAR(75) NOT NULL,
Geboorte_datum DATE NOT NULL,
Leeftijd INT NOT NULL,
Team_ID INT
);
-- Describe the Persoon table--
DESCRIBE Persoon;
-- DROP TABLE Persoon;
-- Create table Team--
CREATE TABLE Team(
Team_ID INT PRIMARY KEY,
Team_naam VARCHAR(100) NOT NULL
);
-- Describe the Team table--
DESCRIBE Team;
-- Create the ICT vaardigheden table--
CREATE TABLE ICT_vaardigheden(
Vaardigheid_ID INT PRIMARY KEY AUTO_INCREMENT,
Omschrijving VARCHAR(100) NOT NULL,
Studenten_nummer INT REFERENCES Persoon(Studenten_nummer)
);
-- Describe the ICT vaardigheden table--
DESCRIBE ICT_vaardigheden;
-- DROP TABLE ICT_vaardigheden;
-- Create the Contact info table--
CREATE TABLE Contact_info(
Studenten_nummer INT PRIMARY KEY,
Unasat_mail VARCHAR(100),
Telefoon_nummer INT,
Adres VARCHAR(150)
);
-- Describe the contact info table--
DESCRIBE Contact_info;
-- Add the contraints to the tables--
ALTER TABLE Persoon
ADD CONSTRAINT fk_team_ID
FOREIGN KEY(Team_ID)
REFERENCES Team(Team_ID);
-- 
ALTER TABLE ICT_vaardigheden
ADD CONSTRAINT fk_Studenten_nummer
FOREIGN KEY(Studenten_nummer)
REFERENCES Persoon(Studenten_nummer);
-- 
ALTER TABLE Contact_info
ADD CONSTRAINT Fk_Contact_ID
FOREIGN KEY(Studenten_nummer)
REFERENCES Persoon(Studenten_nummer);
--


