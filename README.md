## Getting Started

Welcome to our repository for end of semester project for semester 2 
ICT Delivery: Java & Databases at the University of Aplied Science And Technology (Unasat)

## Project Details

The goal of this assignment is to develop a console application in Java, 
that teams can use to register for an ICT Hackathon. 
The application must be able to capture team member data and automatically store it in a MySQL database. 
In addition, the application must be able to retrieve information about teams and their members using database queries.
Specifications:
The application must be developed in Java and must have a console interface for data entry.
Each team can consist of a maximum of 4 members. Groups larger than 4 must be explicitly approved by the teachers.

The following data must be recorded and stored in the database:
 - Personal data: Name, surname, student number, age, date of birth.
 - Team details: Team name, team members.
 - Contact details: Contact number, Unasat email address, place of residence.
 - Personal ICT skill: One personal ICT skill per team member (e.g. Java, Database).

The application must be able to retrieve the following information with a query:
 - Name and surname of team members.
 - Whereabouts of team members.
 - Favorite personal IT skill of team members.

### Team members 
Our team consists of the following members: 
 - Adit Bajnath      | BI/1123/006
 - Dimitri Forster   | BI/1123/013
 - Melvin Geysvliet  | BI/1123/014
 - Darryl Lokhai     | BI/1123/020

## Folder Structure

The workspace contains 3 folders by default, where:

- `db` : the folder to maintain database scripts and fixtures
- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
