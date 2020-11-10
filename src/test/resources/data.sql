-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.22


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
