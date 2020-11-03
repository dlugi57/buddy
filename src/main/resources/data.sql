/*CREATE DATABASE  IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8;
USE `paymybuddy`;*/
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.22

--
-- Table structure for table `user_contacts`
--
START TRANSACTION;
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `user_id` int NOT NULL,
  `contact_id` int NOT NULL,
  PRIMARY KEY (`contact_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
CREATE TABLE `bank_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `bank_transfer`
--

DROP TABLE IF EXISTS `bank_transfer`;
CREATE TABLE `bank_transfer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transfer_type` int DEFAULT NULL,
  `bank_account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `transfer`
--

DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `from_user_id` int DEFAULT NULL,
  `to_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `wallet` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table keys for table `contacts`
--

ALTER TABLE `contacts`
  ADD KEY `FKf9pc3faerry2wp3xnahv2b0rg` (`user_id`);

--
-- Table keys for table `bank_account`
--

ALTER TABLE `bank_account`
  ADD KEY `FK92iik4jwhk7q385jubl2bc2mm` (`user_id`);

--
-- Table keys for table `bank_transfer`
--

ALTER TABLE `bank_transfer`
  ADD KEY `FK175t1a2rxuav4nphc1pdikk6e` (`bank_account_id`);

--
-- Table keys for table `transfer`
--

ALTER TABLE `transfer`
  ADD KEY `FKmxrdu03dy9fhi2nb0toxx3jqy` (`from_user_id`),
  ADD KEY `FKmsjil4f334lasokc5hs79x37t` (`to_user_id`);

--
-- Table secondary keys for table `user_contacts`
--

ALTER TABLE `contacts`
  ADD CONSTRAINT `FKrs3dlygg4whsg3wi9eiuh58m7` FOREIGN KEY (`contact_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKf9pc3faerry2wp3xnahv2b0rg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Table secondary keys for table `bank_account`
--

ALTER TABLE `bank_account`
  ADD CONSTRAINT `FK92iik4jwhk7q385jubl2bc2mm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Table secondary keys for table `bank_transfer`
--

ALTER TABLE `bank_transfer`
  ADD CONSTRAINT `FK175t1a2rxuav4nphc1pdikk6e` FOREIGN KEY (`bank_account_id`) REFERENCES `user` (`id`);

--
-- Table secondary keys for table `transfer`
--

ALTER TABLE `transfer`
  ADD CONSTRAINT `FKmsjil4f334lasokc5hs79x37t` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKmxrdu03dy9fhi2nb0toxx3jqy` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`);

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES
    (1,'2020-10-31 23:40:25.737000','user@email.com','UserFirstName1','UserLastName1','password1',9945),
    (2,'2020-10-31 23:40:33.146000','user2@email.com','UserFirstName2','UserLastName2','password2',10945),
    (3,'2020-10-31 23:40:33.146000','user3@email.com','UserFirstName3','UserLastName3','password3',7935),
    (4,'2020-10-31 23:39:59.609000','user4@email.com','UserFirstName4','UserLastName4','password4',9955),
    (5,'2020-10-31 23:20:31.454000','user5@email.com','UserFirstName5','UserLastName5','password5',8955);

--
-- Dumping data for table `contacts`
--

INSERT INTO `contacts` VALUES
    (1,2),(1,3),(1,4),(1,5),(2,1),(2,3),(2,4),(2,5),(3,1),(3,2),(3,4),(3,5);

--
-- Dumping data for table `bank_account`
--

INSERT INTO `bank_account` VALUES
    (1,'BANKACCOUNT1','1234567890',1),
    (2,'BANKACCOUNT2','1234567890',2),
    (3,'BANKACCOUNT3','1234567890',3),
    (4,'BANKACCOUNT4','1234567890',4),
    (5,'BANKACCOUNT5','1234567890',5);

--
-- Dumping data for table `bank_transfer`
--

INSERT INTO `bank_transfer` VALUES
    (1,10000,'2020-10-31 23:19:13.154000','BANKTRANSFER1',0,1),
    (2,10000,'2020-10-31 23:19:20.448000','BANKTRANSFER2',0,2),
    (3,10000,'2020-10-31 23:19:27.377000','BANKTRANSFER3',0,3),
    (4,10000,'2020-10-31 23:19:32.903000','BANKTRANSFER4',0,4),
    (5,10000,'2020-10-31 23:19:40.009000','BANKTRANSFER5',0,5),
    (6,-1000,'2020-10-31 23:20:31.454000','BANKTRANSFER10',1,5),
    (7,-1000,'2020-10-31 23:20:38.549000','BANKTRANSFER9',1,4);

--
-- Dumping data for table `transfer`
--

INSERT INTO `transfer` VALUES
    (1,1000,'2020-10-31 23:39:14.975000','TRANSFER1',1,2),
    (2,1000,'2020-10-31 23:39:48.841000','TRANSFER2',2,3),
    (3,1000,'2020-10-31 23:39:59.605000','TRANSFER3',3,4),
    (4,1000,'2020-10-31 23:40:25.729000','TRANSFER4',3,1),
    (5,1000,'2020-10-31 23:40:33.142000','TRANSFER5',3,2);

COMMIT;
