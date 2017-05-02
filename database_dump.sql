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
INSERT INTO `Compiles` VALUES ('HotTunes','AvgJoe','Mrs. Robinson','Bookends'),('Jams','Bob','Tell Me Why','Fearless'),('Jams','Bob','Thinking Out Loud','X'),('Musica!!','Kim','A Whole New World','Aladdin'),('Musica!!','Kim','All Too Well','Red'),('Musica!!','Kim','Change','Fearless'),('Musica!!','Kim','Fifteen','Fearless'),('Musica!!','Kim','Mean','Speak Now'),('Musica!!','Kim','Out Of The Woods','1989'),('MyMusic','T-Swift','All Too Well ','Red'),('MyMusic','T-Swift','Change','Fearless'),('MyMusic','T-Swift','Fifteen','Fearless'),('MyMusic','T-Swift','Mean','Speak Now'),('MyMusic','T-Swift','Out Of The Woods','1989'),('Playlist','Heather','All Too Well','Red'),('Playlist','Heather','Fifteen','Fearless'),('Playlist','Heather','Mean','Speak Now'),('Playlist','Heather','Out Of The Woods','1989'),('testPlaylist','Kim','A Whole New World','Aladdin'),('testPlaylist','Kim','All Too Well','Red');
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
INSERT INTO `Owns` VALUES ('Bob','Tell Me Why','Fearless'),('Bob','Thinking Out Loud','X'),('Heather','All Too Well','Red'),('Heather','Fifteen','Fearless'),('Heather','Mean','Speak Now'),('Heather','Out of the Woods','1989'),('Kim','A Whole New World','Aladdin'),('Kim','All Too Well','Red'),('Kim','Change','Fearless'),('Kim','Fifteen','Fearless'),('Kim','Mean','Speak Now'),('Kim','Out of the Woods','1989'),('Kim','testSong','Fearless'),('T-Swift','A Whole New World','Aladdin');
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
INSERT INTO `Playlist` VALUES ('HotTunes','AvgJoe','102'),('Jams','Bob','93'),('Musica!!','Kim','34'),('MyMusic','T-Swift','182'),('Playlist','Heather','56'),('testPlaylist','Kim','0'),('TestPlaylist2','T-Swift','0');
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
INSERT INTO `Song` VALUES ('A Whole New World','Aladdin',2,'thisisatest'),('Change','Fearless',4,'thisisatest'),('Fifteen','Fearless',5,'thisisatest'),('Jump','1984',4,'thisisatest'),('Jump','Breakout',4,'thisisatest'),('Mean','Speak Now',4,'thisisatest'),('Mrs. Robinson','Bookends',4,'thisisatest'),('Out Of The Woods','1989',4,'thisisatest'),('Out of the Woods','Red',5,'thisisatest'),('Photograph','All The Right Reasons',4,'thisisatest'),('Photograph','X',4,'thisisatest'),('Tell Me Why','Fearless',3,'thisisatest'),('testSong','Fearless',42,'thisisatest'),('Thinking Out Loud','X',4,'thisisatest');
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
  `passwordSalt` int(11) DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `passwordHash` varchar(66) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('alann',173344348,'alann@project.com','5E1E8BD376FC93FF35D69186E0A5236F519A45B1CA9FF172E67DE25E89F80A0D'),('AvgJoe',2143506781,'averageJoe@project.com','3960C372C7B0E50AAE2BE43CAC43586EF7A7FF3552953A63970ADA8F953DA7D9'),('BillyBob',1335692315,'billybob@project.com','3FD59E7E410BECF546E1D1CDF5829C42538268EB3CDDFA0E768AC5BC75A41716'),('Bob',1346336761,'bob@project.com','94BAB38E3B6A686DC3F3BF221407A02E8E2483120E1C25F828BE8FE27635BFD4'),('eddShee',804645126,'eddshee@project.com','92E137765D17E5C6B4DA9FDE52F299BFF6CF1D655586A519BC1B7382D38D4EBA'),('Heather',1693781862,'heather@project.com','203183553FBA8435FB49D9C490585236549C6B4EB2A5BC77EB146DA53298EEF7'),('Kim',1301459612,'kim@project.com','4AE65CBC479E857076D49A474360F6D856251DB544101755821B119018BE90D4'),('MagicWalt',1902920230,'magicWalt@project.com','7E913F0DA9CBCC54D480EF128A4A23B82B752F6A9DF4F3FB7E2ACED5FDBAFDC5'),('MsMarilyn',1224772386,'msMarilyn@project.com','DDE961DF09A180A95F78AF4D9594A16F1837F4CA33F852024C711951431F887D'),('Nickelback',167184804,'nickelback@project.com','B13187DC363F3CFD16C0E6292B48427DAA397A01767E883C0BA109E1CB0E5C63'),('PointerSistas',278876439,'pointersistas@project.com','EF63AD1C93E50FCCC831BF6083EE63D7D869B7140299327C48498AD51FA7E1C5'),('Scottie',1589320742,'scott@project.com','AB1889F9CE282C5E7358CEE97AE152C7426CB7C5560DE587A93F972E6593385F'),('sim&gar',771885465,'simonandgarfunkel@project.com','B93281578E4754B6E0117238EDA9A68A740625DE0494A1CDE45C4E08C554091C'),('T-Swift',1654516913,'tswift@project.com','E7262880D2D8F0B4248E6911E8AC95C82FB7AD6F5708B5C84390B5E7B04D0449'),('TheFrankster',857250540,'frank@project.com','17CCD2902C91D48D9479A873B0BE01AFEAA94F208EC12AAF50853B084826FDD3'),('VanHalen',743281976,'vanhalen@project.com','1E7D4AD6C27B3561D70EEC8958AAB63EB950DEFC8F614B4740E8CEEB17AEF76F');
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

-- Dump completed on 2017-05-02  0:04:20
