-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: pitapet
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `banner`
--

DROP TABLE IF EXISTS `banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banner` (
  `BN_num` int NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `BN_url` varchar(100) NOT NULL COMMENT '배너클릭시 이동할 주소',
  `BN_order` int NOT NULL,
  `BN_img` varchar(40) NOT NULL,
  `BN_name` varchar(40) NOT NULL,
  PRIMARY KEY (`BN_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner`
--

LOCK TABLES `banner` WRITE;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `BO_num` int NOT NULL AUTO_INCREMENT COMMENT 'AI 변경',
  `BO_title` varchar(20) NOT NULL,
  `BO_content` varchar(10000) NOT NULL COMMENT '양 제한변경',
  `BO_DATE` date NOT NULL,
  `BO_photo` varchar(100) DEFAULT NULL COMMENT '양 제한변경',
  `BO_name` varchar(10) NOT NULL,
  `BO_heart` int DEFAULT NULL,
  `BO_modifydate` datetime DEFAULT NULL,
  `BO_views` int NOT NULL,
  `BO_status` int NOT NULL COMMENT '0:일반 1:삭제 2:신고먹음 3:신고에의한 삭제',
  `UI_id` varchar(50) NOT NULL,
  `CA_num` int NOT NULL,
  `BO_pstatus` varchar(45) DEFAULT NULL COMMENT '추가함',
  `BO_notice` int DEFAULT NULL COMMENT '추가함 / 0:일반 1:공지',
  `pl_name` varchar(300) DEFAULT NULL COMMENT '추가함',
  `pl_lat` double DEFAULT NULL COMMENT '추가함',
  `pl_lon` double DEFAULT NULL COMMENT '추가함',
  PRIMARY KEY (`BO_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `boardcomment`
--

DROP TABLE IF EXISTS `boardcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boardcomment` (
  `CO_num` int NOT NULL AUTO_INCREMENT COMMENT 'AI 변경',
  `CO_content` varchar(500) NOT NULL,
  `CO_date` date NOT NULL,
  `CO_status` int NOT NULL COMMENT '이름변경 / 0:일반 1:삭제 2:신고먹음',
  `CO_heart` int DEFAULT NULL,
  `CO_modifydate` datetime DEFAULT NULL,
  `UI_id` varchar(50) NOT NULL,
  `BO_num` int DEFAULT NULL COMMENT '추가',
  PRIMARY KEY (`CO_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boardcomment`
--

LOCK TABLES `boardcomment` WRITE;
/*!40000 ALTER TABLE `boardcomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `boardcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendar` (
  `CD_id` int NOT NULL AUTO_INCREMENT COMMENT '스케줄 내용(CD_info)사라지고 기본키(CD_id) 추가',
  `CD_title` varchar(30) NOT NULL COMMENT 'name -> title 변경',
  `CD_start` datetime NOT NULL COMMENT 'starttime 사라지고 여기 합침',
  `CD_end` datetime NOT NULL COMMENT 'endtime 사라지고 여기 합침',
  `CD_allday` tinyint(1) DEFAULT '0' COMMENT '추가됨',
  `UI_id` varchar(50) NOT NULL,
  PRIMARY KEY (`CD_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar`
--

LOCK TABLES `calendar` WRITE;
/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `CA_num` int NOT NULL,
  `CA_lcnum` int NOT NULL,
  `CA_lcname` varchar(20) NOT NULL,
  `CA_mcnum` int NOT NULL,
  `CA_mcname` varchar(20) NOT NULL,
  `CA_scnum` int DEFAULT NULL,
  `CA_scname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`CA_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `CH_num` int NOT NULL AUTO_INCREMENT COMMENT 'AI변경',
  `CH_fromid` varchar(20) NOT NULL,
  `CH_toid` varchar(20) NOT NULL,
  `CH_content` varchar(100) NOT NULL,
  `CH_sendtime` datetime NOT NULL,
  `CH_sellidx` int DEFAULT NULL,
  `CH_status` int DEFAULT NULL,
  PRIMARY KEY (`CH_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `EV_id` int NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `EV_startdate` date DEFAULT NULL,
  `EV_enddate` date DEFAULT NULL,
  `EV_pic` varchar(40) DEFAULT NULL,
  `EV_url` varchar(100) DEFAULT NULL,
  `EV_loc` varchar(20) DEFAULT NULL,
  `EV_name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`EV_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geoinfo`
--

DROP TABLE IF EXISTS `geoinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `geoinfo` (
  `gno` int NOT NULL AUTO_INCREMENT,
  `glat` varchar(45) DEFAULT NULL,
  `glon` varchar(45) DEFAULT NULL,
  `gname` varchar(45) DEFAULT NULL,
  `gpaldo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geoinfo`
--

LOCK TABLES `geoinfo` WRITE;
/*!40000 ALTER TABLE `geoinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `geoinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `heart`
--

DROP TABLE IF EXISTS `heart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `heart` (
  `HE_num` int NOT NULL AUTO_INCREMENT COMMENT 'AI 추가',
  `UI_id` varchar(15) DEFAULT NULL COMMENT '추가',
  `BO_num` int DEFAULT NULL COMMENT '추가',
  `CO_num` int DEFAULT NULL COMMENT '추가',
  `HE_status` int DEFAULT NULL COMMENT '추가',
  PRIMARY KEY (`HE_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `heart`
--

LOCK TABLES `heart` WRITE;
/*!40000 ALTER TABLE `heart` DISABLE KEYS */;
/*!40000 ALTER TABLE `heart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `joinstat`
--

DROP TABLE IF EXISTS `joinstat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `joinstat` (
  `JS_id` int NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `JS_date` date NOT NULL,
  `JS_status` int DEFAULT NULL COMMENT '1이면가입 2면탈퇴',
  PRIMARY KEY (`JS_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `joinstat`
--

LOCK TABLES `joinstat` WRITE;
/*!40000 ALTER TABLE `joinstat` DISABLE KEYS */;
INSERT INTO `joinstat` VALUES (1,'2022-10-11',1);
/*!40000 ALTER TABLE `joinstat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginstat`
--

DROP TABLE IF EXISTS `loginstat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loginstat` (
  `LS_id` int NOT NULL AUTO_INCREMENT COMMENT 'AI',
  `LS_date` date DEFAULT NULL,
  `UI_id` varchar(30) DEFAULT NULL COMMENT '변경',
  PRIMARY KEY (`LS_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginstat`
--

LOCK TABLES `loginstat` WRITE;
/*!40000 ALTER TABLE `loginstat` DISABLE KEYS */;
INSERT INTO `loginstat` VALUES (1,'2022-10-11','lovelee0821');
/*!40000 ALTER TABLE `loginstat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map`
--

DROP TABLE IF EXISTS `map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `map` (
  `pl_num` int NOT NULL AUTO_INCREMENT,
  `pl_name` varchar(300) NOT NULL,
  `pl_lat` double NOT NULL,
  `pl_lon` double NOT NULL,
  `pl_category` varchar(50) NOT NULL,
  `pl_phnum` varchar(45) DEFAULT NULL,
  `pl_url` varchar(45) DEFAULT NULL,
  `UI_id` varchar(50) NOT NULL,
  PRIMARY KEY (`pl_num`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map`
--

LOCK TABLES `map` WRITE;
/*!40000 ALTER TABLE `map` DISABLE KEYS */;
INSERT INTO `map` VALUES (1,'진미통닭',37.2796325122984,127.018122640024,'식당','','','lovelee0821');
/*!40000 ALTER TABLE `map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memo`
--

DROP TABLE IF EXISTS `memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memo` (
  `MM_mno` int NOT NULL AUTO_INCREMENT COMMENT '추가됨',
  `MM_title` varchar(40) NOT NULL,
  `MM_date` date NOT NULL,
  `MM_contents` varchar(40) NOT NULL,
  `MM_fileName` varchar(40) DEFAULT NULL,
  `UI_id` varchar(50) NOT NULL,
  PRIMARY KEY (`MM_mno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memo`
--

LOCK TABLES `memo` WRITE;
/*!40000 ALTER TABLE `memo` DISABLE KEYS */;
/*!40000 ALTER TABLE `memo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `UI_id` varchar(50) NOT NULL,
  `UI_pw` varchar(20) DEFAULT NULL,
  `UI_name` varchar(10) DEFAULT NULL,
  `UI_ncName` varchar(15) NOT NULL,
  `UI_email` varchar(40) NOT NULL,
  `UI_phonenum` varchar(13) DEFAULT NULL,
  `UI_loc` varchar(40) DEFAULT NULL,
  `UI_birth` date DEFAULT NULL,
  `UI_prohibit` int DEFAULT NULL COMMENT '0은 정상 1은 게시판정지 2는 로그인정지',
  `UI_stprohibit` date DEFAULT NULL,
  `UI_enprohibit` date DEFAULT NULL,
  `UI_img` varchar(40) DEFAULT NULL,
  `UI_grade` varchar(10) DEFAULT NULL,
  `UI_registerdate` datetime DEFAULT NULL,
  `UI_lastlogin` datetime DEFAULT NULL,
  `UI_lastlogout` datetime DEFAULT NULL,
  `UI_status` int NOT NULL COMMENT '0은 일반 1은 휴면 2는 탈퇴',
  `UI_registertype` int DEFAULT NULL COMMENT '새로추가함. null은 일반회원 1은카카오 2는네이버',
  PRIMARY KEY (`UI_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES ('lovelee0821','abc123','김혜진','멍멍','lovelee0821@naver.com','010-7641-9591','용인','2000-01-30',0,NULL,NULL,'','평회원','2022-10-11 11:14:50',NULL,NULL,0,0);
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-11 11:34:24