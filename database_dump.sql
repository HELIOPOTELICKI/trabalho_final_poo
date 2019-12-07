CREATE DATABASE  IF NOT EXISTS `trabalho_final_poo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `trabalho_final_poo`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: trabalho_final_poo
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.8-MariaDB

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
-- Table structure for table `autor`
--

DROP TABLE IF EXISTS `autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autor` (
  `cd_autor` int(11) NOT NULL AUTO_INCREMENT,
  `nm_autor` varchar(50) NOT NULL,
  PRIMARY KEY (`cd_autor`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `editora`
--

DROP TABLE IF EXISTS `editora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `editora` (
  `cd_editora` int(11) NOT NULL AUTO_INCREMENT,
  `nm_editora` varchar(50) NOT NULL,
  PRIMARY KEY (`cd_editora`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entidade`
--

DROP TABLE IF EXISTS `entidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entidade` (
  `cd_entidade` int(11) NOT NULL AUTO_INCREMENT,
  `nm_entidade` varchar(50) NOT NULL,
  `tp_entidade` enum('GO','NG') NOT NULL,
  PRIMARY KEY (`cd_entidade`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `local_publicacao`
--

DROP TABLE IF EXISTS `local_publicacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `local_publicacao` (
  `cd_local_publicacao` int(11) NOT NULL AUTO_INCREMENT,
  `nm_local_publicacao` varchar(50) NOT NULL,
  PRIMARY KEY (`cd_local_publicacao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `cd_material` int(11) NOT NULL AUTO_INCREMENT,
  `nm_titulo` varchar(100) NOT NULL,
  `ds_ano_producao` varchar(10) NOT NULL,
  `ds_ano_publicacao` varchar(10) NOT NULL,
  `ds_edicao` varchar(10) NOT NULL,
  `nr_paginas` varchar(10) NOT NULL,
  `ds_url_disponivel` varchar(100) NOT NULL,
  `nr_isbn` varchar(30) NOT NULL,
  `nr_issn` varchar(30) NOT NULL,
  `cd_tp_material` int(11) NOT NULL,
  `cd_tp_divulgacao` int(11) NOT NULL,
  `cd_local_publicacao` int(11) NOT NULL,
  `cd_editora` int(11) NOT NULL,
  `cd_entidade` int(11) NOT NULL,
  PRIMARY KEY (`cd_material`),
  UNIQUE KEY `nm_titulo_UNIQUE` (`nm_titulo`),
  KEY `material_editora` (`cd_editora`),
  KEY `material_entidade` (`cd_entidade`),
  KEY `material_local_publicacao` (`cd_local_publicacao`),
  KEY `material_tipo_divulgacao` (`cd_tp_divulgacao`),
  KEY `material_tipo_material_idx` (`cd_tp_material`),
  CONSTRAINT `material_editora` FOREIGN KEY (`cd_editora`) REFERENCES `editora` (`cd_editora`),
  CONSTRAINT `material_entidade` FOREIGN KEY (`cd_entidade`) REFERENCES `entidade` (`cd_entidade`),
  CONSTRAINT `material_local_publicacao` FOREIGN KEY (`cd_local_publicacao`) REFERENCES `local_publicacao` (`cd_local_publicacao`),
  CONSTRAINT `material_tipo_divulgacao` FOREIGN KEY (`cd_tp_divulgacao`) REFERENCES `meio_divulgacao` (`tp_divulgacao`),
  CONSTRAINT `material_tipo_material` FOREIGN KEY (`cd_tp_material`) REFERENCES `tipo_material` (`tp_material`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `material_autor`
--

DROP TABLE IF EXISTS `material_autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_autor` (
  `cd_material` int(11) NOT NULL,
  `cd_autor` int(11) NOT NULL,
  PRIMARY KEY (`cd_material`,`cd_autor`),
  KEY `material_autor_autor_idx` (`cd_autor`),
  CONSTRAINT `material_autor_autor` FOREIGN KEY (`cd_autor`) REFERENCES `autor` (`cd_autor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `material_autor_material` FOREIGN KEY (`cd_material`) REFERENCES `material` (`cd_material`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `material_palavra_chave`
--

DROP TABLE IF EXISTS `material_palavra_chave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_palavra_chave` (
  `cd_palavra_chave` int(11) NOT NULL,
  `cd_material` int(11) NOT NULL,
  PRIMARY KEY (`cd_palavra_chave`,`cd_material`),
  KEY `material_palavra_chave_material` (`cd_material`),
  CONSTRAINT `material_palavra_chave_cd_palavra_chave` FOREIGN KEY (`cd_palavra_chave`) REFERENCES `palavra_chave` (`cd_palavra_chave`),
  CONSTRAINT `material_palavra_chave_material` FOREIGN KEY (`cd_material`) REFERENCES `material` (`cd_material`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meio_divulgacao`
--

DROP TABLE IF EXISTS `meio_divulgacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meio_divulgacao` (
  `tp_divulgacao` int(11) NOT NULL AUTO_INCREMENT,
  `ds_divulgacao` varchar(50) NOT NULL,
  PRIMARY KEY (`tp_divulgacao`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `palavra_chave`
--

DROP TABLE IF EXISTS `palavra_chave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `palavra_chave` (
  `cd_palavra_chave` int(11) NOT NULL AUTO_INCREMENT,
  `ds_palavra_chave` varchar(50) NOT NULL,
  PRIMARY KEY (`cd_palavra_chave`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_material`
--

DROP TABLE IF EXISTS `tipo_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_material` (
  `tp_material` int(11) NOT NULL AUTO_INCREMENT,
  `ds_material` varchar(50) NOT NULL,
  PRIMARY KEY (`tp_material`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-06 21:48:00
