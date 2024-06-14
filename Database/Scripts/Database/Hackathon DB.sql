-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema beroepsproduct
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema beroepsproduct
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `beroepsproduct` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `beroepsproduct` ;

-- -----------------------------------------------------
-- Table `beroepsproduct`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`team` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naam` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `naam` (`naam` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beroepsproduct`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`person` (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `voornaam` VARCHAR(30) NOT NULL,
  `achternaam` VARCHAR(30) NOT NULL,
  `geboorte_datum` DATE NOT NULL,
  `leeftijd` INT NULL DEFAULT NULL,
  `team_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  INDEX `person_fk_1` (`team_id` ASC) VISIBLE,
  CONSTRAINT `person_fk_1`
    FOREIGN KEY (`team_id`)
    REFERENCES `beroepsproduct`.`team` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 57
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beroepsproduct`.`contact_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`contact_info` (
  `contact_id` INT NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `telefoon_nummer` VARCHAR(30) NULL DEFAULT NULL,
  `mobiel_nummer` VARCHAR(30) NOT NULL,
  `adres` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`contact_id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE,
  CONSTRAINT `contact_info_fk_1`
    FOREIGN KEY (`contact_id`)
    REFERENCES `beroepsproduct`.`person` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `beroepsproduct`.`ict_vaardigheid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`ict_vaardigheid` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `omschrijving` VARCHAR(30) NOT NULL,
  `student_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `ict_vaardigheid_fk_1` (`student_id` ASC) VISIBLE,
  CONSTRAINT `ict_vaardigheid_fk_1`
    FOREIGN KEY (`student_id`)
    REFERENCES `beroepsproduct`.`person` (`student_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `beroepsproduct` ;

-- -----------------------------------------------------
-- Placeholder table for view `beroepsproduct`.`teamleden`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`teamleden` (`voornaam` INT, `achternaam` INT, `team` INT);

-- -----------------------------------------------------
-- Placeholder table for view `beroepsproduct`.`teamleden_adres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`teamleden_adres` (`voornaam` INT, `achternaam` INT, `team` INT, `adres` INT);

-- -----------------------------------------------------
-- Placeholder table for view `beroepsproduct`.`teamleden_vaardigheden`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `beroepsproduct`.`teamleden_vaardigheden` (`voornaam` INT, `achternaam` INT, `team` INT, `omschrijving` INT);

-- -----------------------------------------------------
-- View `beroepsproduct`.`teamleden`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beroepsproduct`.`teamleden`;
USE `beroepsproduct`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `beroepsproduct`.`teamleden` AS select `beroepsproduct`.`person`.`voornaam` AS `voornaam`,`beroepsproduct`.`person`.`achternaam` AS `achternaam`,`beroepsproduct`.`team`.`naam` AS `team` from (`beroepsproduct`.`person` join `beroepsproduct`.`team` on((`beroepsproduct`.`person`.`team_id` = `beroepsproduct`.`team`.`id`)));

-- -----------------------------------------------------
-- View `beroepsproduct`.`teamleden_adres`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beroepsproduct`.`teamleden_adres`;
USE `beroepsproduct`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `beroepsproduct`.`teamleden_adres` AS select `beroepsproduct`.`person`.`voornaam` AS `voornaam`,`beroepsproduct`.`person`.`achternaam` AS `achternaam`,`beroepsproduct`.`team`.`naam` AS `team`,`beroepsproduct`.`contact_info`.`adres` AS `adres` from ((`beroepsproduct`.`person` join `beroepsproduct`.`team` on((`beroepsproduct`.`person`.`team_id` = `beroepsproduct`.`team`.`id`))) left join `beroepsproduct`.`contact_info` on((`beroepsproduct`.`person`.`student_id` = `beroepsproduct`.`contact_info`.`contact_id`)));

-- -----------------------------------------------------
-- View `beroepsproduct`.`teamleden_vaardigheden`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beroepsproduct`.`teamleden_vaardigheden`;
USE `beroepsproduct`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `beroepsproduct`.`teamleden_vaardigheden` AS select `beroepsproduct`.`person`.`voornaam` AS `voornaam`,`beroepsproduct`.`person`.`achternaam` AS `achternaam`,`beroepsproduct`.`team`.`naam` AS `team`,`beroepsproduct`.`ict_vaardigheid`.`omschrijving` AS `omschrijving` from ((`beroepsproduct`.`person` join `beroepsproduct`.`team` on((`beroepsproduct`.`person`.`team_id` = `beroepsproduct`.`team`.`id`))) join `beroepsproduct`.`ict_vaardigheid` on((`beroepsproduct`.`person`.`student_id` = `beroepsproduct`.`ict_vaardigheid`.`student_id`)));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
