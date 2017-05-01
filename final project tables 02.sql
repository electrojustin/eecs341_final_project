-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema muzikr
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema muzikr
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `muzikr` DEFAULT CHARACTER SET utf8 ;
USE `muzikr` ;

-- -----------------------------------------------------
-- Table `muzikr`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`User` (
  `username` VARCHAR(45) NOT NULL,
  `passwordHash` VARCHAR(45) NULL,
  `passwordSalt` INT NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Artist` (
  `artistID` INT NOT NULL,
  `artistName` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  PRIMARY KEY (`artistID`),
  INDEX `username_idx` (`username` ASC),
  CONSTRAINT `username`
    FOREIGN KEY (`username`)
    REFERENCES `muzikr`.`User` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Song`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Song` (
  `songName` VARCHAR(45) NOT NULL,
  `albumName` VARCHAR(45) NOT NULL,
  `length` INT NULL,
  `file` VARCHAR(45) NULL,
  PRIMARY KEY (`songName`, `albumName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Album` (
  `albumName` VARCHAR(45) NOT NULL,
  `genre` VARCHAR(45) NULL,
  `labelName` VARCHAR(45) NULL,
  PRIMARY KEY (`albumName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Label` (
  `labelName` VARCHAR(45) NOT NULL,
  `studioAddress` VARCHAR(45) NULL,
  `presidentName` VARCHAR(45) NULL,
  `presidentUsername` VARCHAR(45) NULL,
  PRIMARY KEY (`labelName`),
  INDEX `presidentUsername_idx` (`presidentUsername` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Playlist` (
  `playlistName` VARCHAR(45) NOT NULL,
  `userOwner` VARCHAR(45) NOT NULL,
  `length` VARCHAR(45) NULL,
  PRIMARY KEY (`playlistName`, `userOwner`),
  INDEX `username_idx` (`userOwner` ASC),
  CONSTRAINT `userOwner`
    FOREIGN KEY (`userOwner`)
    REFERENCES `muzikr`.`User` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Creates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Creates` (
  `artistID` INT NOT NULL,
  `songName` VARCHAR(45) NOT NULL,
  `albumName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`artistID`, `songName`, `albumName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Owns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Owns` (
  `username` VARCHAR(45) NOT NULL,
  `songName` VARCHAR(45) NOT NULL,
  `albumName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`, `songName`, `albumName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `muzikr`.`Compiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `muzikr`.`Compiles` (
  `playlistName` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `songName` VARCHAR(45) NOT NULL,
  `albumName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`playlistName`, `username`, `songName`, `albumName`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
