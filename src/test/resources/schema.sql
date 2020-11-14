-- -----------------------------------------------------
-- Schema
-- -----------------------------------------------------

START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE  IF NOT EXISTS `paymybuddytest` DEFAULT CHARACTER SET utf8;
USE `paymybuddytest`;

--
-- Table structure for table `contacts`
--
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
                            `user_id` int NOT NULL,
                            `contact_id` int NOT NULL,
                            PRIMARY KEY (`contact_id`,`user_id`)
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
