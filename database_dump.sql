-- MySQL dump 10.16  Distrib 10.1.22-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: muzikr
-- ------------------------------------------------------
-- Server version	10.1.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Album`
--

DROP TABLE IF EXISTS `Album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Album` (
  `albumName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `genre` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `labelName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`albumName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Album`
--

LOCK TABLES `Album` WRITE;
/*!40000 ALTER TABLE `Album` DISABLE KEYS */;
INSERT INTO `Album` VALUES ('1984','Rock','Warner Bros.'),('1989','Pop','Big Machine'),('Aladdin','Musical','Disney'),('Bookends','Folk Rock','Columbia Records'),('Break Out','Pop','Planet'),('Fearless','Country','Big Machine'),('Red','Country','Big Machine'),('Speak Now','Country','Big Machine'),('TestAlbum','test','labelName'),('X','Pop','Asylum');
/*!40000 ALTER TABLE `Album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artist`
--

DROP TABLE IF EXISTS `Artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artist` (
  `artistID` int(11) NOT NULL,
  `artistName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`artistID`),
  KEY `username_idx` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `User` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artist`
--

LOCK TABLES `Artist` WRITE;
/*!40000 ALTER TABLE `Artist` DISABLE KEYS */;
INSERT INTO `Artist` VALUES (1,'Alan Menken','alann'),(2,'Taylor Swift','T-Swift'),(3,'Ed Sheeran','eddShee'),(4,'The Pointer Sisters','PointerSistas'),(5,'Nickelback','Nickelback'),(6,'Van Halen','VanHalen'),(7,'Simon and Garfunkel','sim&gar');
/*!40000 ALTER TABLE `Artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Compiles`
--

DROP TABLE IF EXISTS `Compiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Compiles` (
  `playlistName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `songName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `albumName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`playlistName`,`username`,`songName`,`albumName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Compiles`
--

LOCK TABLES `Compiles` WRITE;
/*!40000 ALTER TABLE `Compiles` DISABLE KEYS */;
INSERT INTO `Compiles` VALUES ('HotTunes','AvgJoe','Mrs. Robinson','Bookends'),('Jams','Bob','Tell Me Why','Fearless'),('Jams','Bob','Thinking Out Loud','X'),('Musica!!','Kim','A Whole New World','Aladdin'),('Musica!!','Kim','All Too Well','Red'),('Musica!!','Kim','Change','Fearless'),('Musica!!','Kim','Fifteen','Fearless'),('Musica!!','Kim','Out Of The Woods','1989'),('MyMusic','T-Swift','All Too Well ','Red'),('MyMusic','T-Swift','Change','Fearless'),('MyMusic','T-Swift','Fifteen','Fearless'),('MyMusic','T-Swift','Mean','Speak Now'),('MyMusic','T-Swift','Out Of The Woods','1989'),('Playlist','Heather','All Too Well','Red'),('Playlist','Heather','Fifteen','Fearless'),('Playlist','Heather','Mean','Speak Now'),('Playlist','Heather','Out Of The Woods','1989');
/*!40000 ALTER TABLE `Compiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Creates`
--

DROP TABLE IF EXISTS `Creates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Creates` (
  `artistID` int(11) NOT NULL,
  `songName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `albumName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`artistID`,`songName`,`albumName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Creates`
--

LOCK TABLES `Creates` WRITE;
/*!40000 ALTER TABLE `Creates` DISABLE KEYS */;
INSERT INTO `Creates` VALUES (1,'A Whole New World','Aladdin'),(2,'Change','Fearless'),(2,'Fifteen','Fearless'),(2,'Mean','Speak Now'),(2,'Out Of The Woods','1989'),(2,'Out Of The Woods','Red'),(2,'Tell Me Why','Fearless'),(2,'testSong','Fearless'),(3,'Photograph','X'),(3,'Thinking Out Loud','X'),(4,'Jump','Breakout'),(5,'Photograph','All The Right Reasons'),(6,'Jump','1984'),(7,'Mrs. Robinson','Bookends');
/*!40000 ALTER TABLE `Creates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Label`
--

DROP TABLE IF EXISTS `Label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Label` (
  `labelName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `studioAddress` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `presidentName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `presidentUsername` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`labelName`),
  KEY `presidentUsername_idx` (`presidentUsername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Label`
--

LOCK TABLES `Label` WRITE;
/*!40000 ALTER TABLE `Label` DISABLE KEYS */;
INSERT INTO `Label` VALUES ('Asylum','12 Tulip Road','Frank','TheFrankster'),('Big Machine','4 Corn Lane','Scott','Scottie'),('Columbia Records','14 Rose Way','Joe','AvgJoe'),('Disney','9 Happy Drive','Walt','MagicWalt'),('Planet','7 Jupiter Ave','Marilyn','MsMarilyn'),('Warner Bros.','17 South Lane','Billy','BillyBob');
/*!40000 ALTER TABLE `Label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Owns`
--

DROP TABLE IF EXISTS `Owns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Owns` (
  `username` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `songName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `albumName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`username`,`songName`,`albumName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Owns`
--

LOCK TABLES `Owns` WRITE;
/*!40000 ALTER TABLE `Owns` DISABLE KEYS */;
INSERT INTO `Owns` VALUES ('Bob','Tell Me Why','Fearless'),('Bob','Thinking Out Loud','X'),('Heather','All Too Well','Red'),('Heather','Fifteen','Fearless'),('Heather','Mean','Speak Now'),('Heather','Out of the Woods','1989'),('Kim','A Whole New World','Aladdin'),('Kim','All Too Well','Red'),('Kim','Change','Fearless'),('Kim','Fifteen','Fearless'),('Kim','Out of the Woods','1989');
/*!40000 ALTER TABLE `Owns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Playlist`
--

DROP TABLE IF EXISTS `Playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Playlist` (
  `playlistName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `userOwner` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `length` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`playlistName`,`userOwner`),
  KEY `username_idx` (`userOwner`),
  CONSTRAINT `userOwner` FOREIGN KEY (`userOwner`) REFERENCES `User` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Playlist`
--

LOCK TABLES `Playlist` WRITE;
/*!40000 ALTER TABLE `Playlist` DISABLE KEYS */;
INSERT INTO `Playlist` VALUES ('HotTunes','AvgJoe','102'),('Jams','Bob','93'),('Musica!!','Kim','34'),('MyMusic','T-Swift','182'),('Playlist','Heather','56'),('testPlaylist','Kim','0');
/*!40000 ALTER TABLE `Playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Song`
--

DROP TABLE IF EXISTS `Song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Song` (
  `songName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `albumName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `length` int(11) DEFAULT NULL,
  `file` mediumblob,
  PRIMARY KEY (`songName`,`albumName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Song`
--

LOCK TABLES `Song` WRITE;
/*!40000 ALTER TABLE `Song` DISABLE KEYS */;
INSERT INTO `Song` VALUES ('A Whole New World','Aladdin',2,NULL),('Change','Fearless',4,NULL),('Fifteen','Fearless',5,NULL),('Jump','1984',4,NULL),('Jump','Breakout',4,NULL),('Mean','Speak Now',4,NULL),('Mrs. Robinson','Bookends',4,NULL),('Out Of The Woods','1989',4,NULL),('Out of the Woods','Red',5,NULL),('Photograph','All The Right Reasons',4,NULL),('Photograph','X',4,NULL),('Tell Me Why','Fearless',3,NULL),('testSong','Fearless',42,'��\r\0\n\0[\0.\0S\0h\0e\0l\0l\0C\0l\0a\0s\0s\0I\0n\0f\0o\0]\0\r\0\n\0L\0o\0c\0a\0l\0i\0z\0e\0d\0R\0e\0s\0o\0u\0r\0c\0e\0N\0a\0m\0e\0=\0@\0%\0S\0y\0s\0t\0e\0m\0R\0o\0o\0t\0%\0\\\0s\0y\0s\0t\0e\0m\03\02\0\\\0s\0h\0e\0l\0l\03\02\0.\0d\0l\0l\0,\0-\02\01\07\06\09\0\r\0\n\0I\0c\0o\0n\0R\0e\0s\0o\0u\0r\0c\0e\0=\0%\0S\0y\0s\0t\0e\0m\0R\0o\0o\0t\0%\0\\\0s\0y\0s\0t\0e\0m\03\02\0\\\0i\0m\0a\0g\0e\0r\0e\0s\0.\0d\0l\0l\0,\0-\01\08\03\0\r\0\n\0\r'),('Thinking Out Loud','X',4,NULL);
/*!40000 ALTER TABLE `Song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `username` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `passwordHash` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `passwordSalt` int(11) DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('alann',NULL,NULL,'alann@project.com'),('AvgJoe',NULL,NULL,'averageJoe@project.com'),('BillyBob',NULL,NULL,'billybob@project.com'),('Bob',NULL,NULL,'bob@project.com'),('eddShee',NULL,NULL,'eddshee@project.com'),('Heather',NULL,NULL,'heather@project.com'),('Kim',NULL,NULL,'kim@project.com'),('MagicWalt',NULL,NULL,'magicWalt@project.com'),('MsMarilyn',NULL,NULL,'msMarilyn@project.com'),('Nickelback',NULL,NULL,'nickelback@project.com'),('PointerSistas',NULL,NULL,'pointersistas@project.com'),('Scottie',NULL,NULL,'scott@project.com'),('sim&gar',NULL,NULL,'simonandgarfunkel@project.com'),('T-Swift',NULL,NULL,'tswift@project.com'),('TheFrankster',NULL,NULL,'frank@project.com'),('VanHalen',NULL,NULL,'vanhalen@project.com');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-01 21:09:51
