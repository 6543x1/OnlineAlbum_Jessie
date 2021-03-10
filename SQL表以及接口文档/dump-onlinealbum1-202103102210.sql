-- MySQL dump 10.13  Distrib 5.7.32, for Win64 (x86_64)
--
-- Host: localhost    Database: onlinealbum1
-- ------------------------------------------------------
-- Server version	5.7.32-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder`
(
    `fid`        int(11)                              NOT NULL AUTO_INCREMENT,
    `folderName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
    `path`       varchar(250) COLLATE utf8_unicode_ci NOT NULL,
    `father`     int(11)                              NOT NULL DEFAULT '0',
    `username`   varchar(20) COLLATE utf8_unicode_ci  NOT NULL,
    `size`       bigint(20)                           NOT NULL,
    PRIMARY KEY (`fid`),
    KEY `folder_FK` (`username`),
    CONSTRAINT `folder_FK` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder`
    DISABLE KEYS */;
INSERT INTO `folder`
VALUES (0, 'root', 'D:/TomcatImg/', -1, 'Jessie', 0),
       (2, 'my', 'my/', 5, 'Jessie', 1495968),
       (3, 'my02', 'my/my02/', 2, 'Jessie', 1699492),
       (4, 'my03', 'my/my03/', 2, 'Jessie', 227184),
       (5, 'Jessie', 'D:/TomcatImg/Jessie/', 0, 'Jessie', 393573),
       (6, 'kenzo', 'D:/TomcatImg/kenzo/', 0, 'kenzo', 0),
       (7, 'Guest', 'D:/TomcatImg/Guest/', 0, 'Guest', 112028),
       (8, 'Lin', 'D:/TomcatImg/Lin/', 0, 'Lin', 0),
       (9, 'Alpha', 'D:/TomcatImg/Alpha/', 0, 'Alpha', 0),
       (10, '2021215', 'D:/TomcatImg/2021215/', 0, '2021215', 0),
       (11, '2021214', 'D:/TomcatImg/2021214/', 0, '2021214', 0),
       (12, '2021', 'D:/TomcatImg/2021215/2021/', 10, '2021215', 7050),
       (13, '1406', 'D:/TomcatImg/2021215/1406/', 10, '2021215', 97116),
       (14, 'aaa', 'D:/TomcatImg/aaa/', 0, 'aaa', 0),
       (15, 'a', 'D:/TomcatImg/a/', 0, 'a', 0),
       (16, 'zhou', 'D:/TomcatImg/zhou/', 0, 'zhou', 1630586),
       (18, '我是超威蓝猫', 'my/我是超威蓝猫/', 2, 'Jessie', 0);
/*!40000 ALTER TABLE `folder`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image`
(
    `imageid`    int(11)                              NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `username`   varchar(20) COLLATE utf8_unicode_ci  NOT NULL,
    `album`      varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `path`       varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `visited`    int(11)                              NOT NULL,
    `uploadTime` datetime                             NOT NULL,
    `fid`        int(11)                              NOT NULL,
    `size`       bigint(20)                           NOT NULL,
    PRIMARY KEY (`imageid`),
    KEY `image_FK` (`username`),
    KEY `image_FK_1` (`fid`),
    CONSTRAINT `image_FK` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `image_FK_1` FOREIGN KEY (`fid`) REFERENCES `folder` (`fid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 47
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image`
    DISABLE KEYS */;
INSERT INTO `image`
VALUES (2, 'test', 'Jessie', 'test', 'test', 1, '2021-02-06 20:00:00', 0, 0),
       (3, 'QQ拼音截图20201201204008.png', 'Jessie', 'my', 'my/', 1, '2021-02-07 01:35:01', 0, 0),
       (5, 'd427b5b7d0a20cf4e0adcc7170094b36adaf9980.jpg', 'Jessie', 'my', 'my/', 1, '2021-02-09 19:08:28', 2, 11319),
       (6, 'zitiduizhaobiao-2.jpg', 'Jessie', 'my', 'my/', 1, '2021-02-09 19:10:09', 2, 68088),
       (7, 'ChMkJ1bKwxmIagc-AAaj5o2w6BgAALGxwPWFDYABqP-431.jpg', 'Jessie', 'my', 'my/', 1, '2021-02-09 19:10:53', 2,
        169608),
       (8, 'QQ截图20200430140808.jpg', 'Jessie', 'Default', 'my/', 1, '2021-02-09 20:26:18', 2, 94608),
       (9, 'QQ截图20200408234102.jpg', 'Jessie', 'Default', 'my/', 1, '2021-02-14 23:59:21', 2, 129357),
       (10, 'QQ截图20210214235931.jpg', '2021215', '2021', '2021215/2021/', 1, '2021-02-15 13:55:34', 12, 7050),
       (11, 'QQ截图20210207013757.jpg', '2021215', '1406', '2021215/1406/', 1, '2021-02-15 14:06:47', 13, 97116),
       (12, 'QQ图片20191108084050.jpg', 'Jessie', 'Default', 'my/my03/', 2, '2021-02-15 14:24:34', 4, 102545),
       (13, 'ChMkJ1bKwxmIagc-AAaj5o2w6BgAALGxwPWFDYABqP-431.jpg', 'Jessie', 'Default', 'my/my02/', 1,
        '2021-02-15 14:30:44', 3, 169608),
       (15, 'haha.jpg', 'Jessie', 'my02', 'my/my02/', 1, '2021-02-20 11:21:39', 3, 59765),
       (16, '023746fki.jpg', 'Jessie', 'my03', 'my/my02/', 1, '2021-02-20 11:28:16', 3, 23550),
       (18, '林洁楠031902410第六章参考课件及视频1.jpg', 'Guest', 'Guest', 'Guest/', 1, '2021-02-27 15:27:21', 7, 112028),
       (25, 'mmexport1613611853232.jpg', 'zhou', 'zhou', 'zhou', 1, '2021-02-27 23:22:26', 16, 268328),
       (26, '林洁楠031902410第一章第一节.jpg', 'Jessie', 'my02', 'my/my02/', 1, '2021-02-28 23:31:58', 3, 129941),
       (31, '2021-03-01T00:03:20010_林洁楠031902410第一章第一节.jpg', 'Jessie', 'my02', 'my/my02/', 1, '2021-03-01 00:03:20', 3,
        129941),
       (32, '2021-03-01T00_10_41220_林洁楠031902410第一章第一节.jpg', 'Jessie', 'my02', 'my/my02/', 1, '2021-03-01 00:10:41', 3,
        129941),
       (33, '林洁楠031902410第二章第二节.jpg', 'Jessie', 'my03', 'my/my03/', 2, '2021-03-02 22:36:30', 4, 124639),
       (43, '林洁楠031902410第二章第二节.jpg', 'Jessie', 'my02', 'my/my02/', 1, '2021-03-02 23:56:20', 3, 124639),
       (44, '-345b89019154d6d6.gif', 'Jessie', 'my02', 'my/my02/', 1, '2021-03-06 00:03:10', 3, 313628),
       (45, '2021-03-08T21_32_50069_-345b89019154d6d6.gif', 'Jessie', 'my02', 'my/my02/', 1, '2021-03-08 21:32:50', 3,
        313628),
       (46, 'TIM截图20190129162119.png', 'Jessie', 'my', 'my/', 1, '2021-03-09 00:13:00', 2, 1102395);
/*!40000 ALTER TABLE `image`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `share`
--

DROP TABLE IF EXISTS `share`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share`
(
    `shareType` tinyint(4)                          NOT NULL,
    `shareUser` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
    `shareCode` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
    `shareData` int(11)                             NOT NULL,
    KEY `share_FK` (`shareUser`),
    CONSTRAINT `share_FK` FOREIGN KEY (`shareUser`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `share`
--

LOCK TABLES `share` WRITE;
/*!40000 ALTER TABLE `share`
    DISABLE KEYS */;
INSERT INTO `share`
VALUES (0, 'Jessie', '9td8M9', 4);
/*!40000 ALTER TABLE `share`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user`
(
    `username`    varchar(20) COLLATE utf8_unicode_ci NOT NULL,
    `password`    varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `userfid`     int(11)                              DEFAULT NULL,
    `mailAddress` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES ('2021214', '3c5aabf671ce11f1a7d84a229f4eac62', 11, NULL),
       ('2021215', 'e10adc3949ba59abbe56e057f20f883e', 10, NULL),
       ('a', 'e10adc3949ba59abbe56e057f20f883e', 15, NULL),
       ('aaa', 'e10adc3949ba59abbe56e057f20f883e', 14, NULL),
       ('Alpha', 'e10adc3949ba59abbe56e057f20f883e', 9, NULL),
       ('Guest', '084e0343a0486ff05530df6c705c8bb4', 7, NULL),
       ('Jessie', 'e10adc3949ba59abbe56e057f20f883e', 5, '1647389906@qq.com'),
       ('kenzo', '25d55ad283aa400af464c76d713c07ad', 6, NULL),
       ('Lin', 'e10adc3949ba59abbe56e057f20f883e', 8, NULL),
       ('zhou', 'e10adc3949ba59abbe56e057f20f883e', 16, NULL);
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'onlinealbum1'
--
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-03-10 22:10:06
