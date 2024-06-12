-- Query voor een overzicht van teamleden per team
CREATE VIEW Teamleden AS
SELECT person.voornaam, person.achternaam, team.naam team 
FROM person 
INNER JOIN team 
ON person.team_id = team.id;

-- Query voor een overzicht van teamleden en hun adres
/*SELECT person.voornaam, person.achternaam, team.naam team, contact_info.adres 
FROM person 
INNER JOIN team ON person.student_id = team.id 
INNER JOIN contact_info 
ON person.student_id = contact_info.contact_id;*/

-- Aangepaste query voor een overzicht van alle teamleden, hun team en adress--
CREATE VIEW Teamleden_Adres AS
SELECT person.voornaam, person.achternaam, team.naam AS team, contact_info.adres 
FROM person 
INNER JOIN team 
ON person.team_id = team.id 
LEFT JOIN contact_info 
ON person.student_id = contact_id;

-- Query voor een overzicht van teamleden en hun ICT vaardigheden
CREATE VIEW Teamleden_Vaardigheden AS
SELECT person.voornaam, person.achternaam, team.naam AS team, ict_vaardigheid.omschrijving 
FROM person 
INNER JOIN team 
ON person.student_id = team.id 
INNER JOIN ict_vaardigheid 
ON person.student_id = ict_vaardigheid.student_id;
    



