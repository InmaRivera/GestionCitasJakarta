-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gestioncitas
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `citas`
--

DROP TABLE IF EXISTS `citas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citas` (
  `idCita` int NOT NULL AUTO_INCREMENT,
  `horaCita` time DEFAULT NULL,
  `fechaCita` date DEFAULT NULL,
  `idClienteFK` int DEFAULT NULL,
  `idTrabajadorFK` int DEFAULT NULL,
  `idServicioFK` int DEFAULT NULL,
  PRIMARY KEY (`idCita`),
  KEY `idClienteFK_idx` (`idClienteFK`),
  KEY `idTrabajadorFK_idx` (`idTrabajadorFK`),
  KEY `idServicioFK_idx` (`idServicioFK`),
  CONSTRAINT `idClienteFK` FOREIGN KEY (`idClienteFK`) REFERENCES `clientes` (`idCliente`),
  CONSTRAINT `idServicioFK` FOREIGN KEY (`idServicioFK`) REFERENCES `servicios` (`idServicio`),
  CONSTRAINT `idTrabajadorFK` FOREIGN KEY (`idTrabajadorFK`) REFERENCES `trabajadores` (`idTrabajador`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citas`
--

LOCK TABLES `citas` WRITE;
/*!40000 ALTER TABLE `citas` DISABLE KEYS */;
INSERT INTO `citas` VALUES (4,'10:00:00','2023-05-01',11,1,1),(7,'10:00:00','2023-06-07',9,1,4);
/*!40000 ALTER TABLE `citas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `idUsuarioFK` int DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  KEY `idUsuarioFK_idx` (`idUsuarioFK`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,2),(5,9),(7,11),(9,13),(10,14),(11,15),(16,20),(17,21),(18,22),(21,25),(22,26),(25,29),(26,30),(31,34),(35,38);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicios`
--

DROP TABLE IF EXISTS `servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios` (
  `idServicio` int NOT NULL AUTO_INCREMENT,
  `tipoServicio` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `duracionServicio` time DEFAULT NULL,
  `descripcionServicio` varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `precioServicio` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`idServicio`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios`
--

LOCK TABLES `servicios` WRITE;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
INSERT INTO `servicios` VALUES (1,'Esmalte permanente','01:00:00','Manicura permanente sobre uña natural',12.00),(2,'Manicura acrílica','01:30:00','Manicura acrilica sobre uña natural o moldeando largura,',22.00),(3,'Manicura gel','01:30:00','Manicura en gel sobre uña natural o moldeando largo ',22.00),(4,'Cera medias piernas ','00:30:00','Cera en la mitad de las piernas, zona elegida ',8.00),(5,'Cera piernas completas','00:50:00','Cera piernas completas',12.00),(6,'Cera zona pequeña ','00:05:00','Cera zona pequeñas a elegir ',4.00),(7,'Pedicura ','01:00:00','Eliminar durezas en los pies',10.00),(8,'Pedicura + esmalte permanente','01:30:00','Elimnar durezas más esmaltado permanente en pies ',22.00),(9,'Esmaltado permanente en pies ','01:00:00','Solo esmaltado permanente en pies ',12.00);
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajadores`
--

DROP TABLE IF EXISTS `trabajadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trabajadores` (
  `idTrabajador` int NOT NULL AUTO_INCREMENT,
  `idUsuarioFK` int DEFAULT NULL,
  PRIMARY KEY (`idTrabajador`),
  KEY `idUsuarioFK_idx` (`idUsuarioFK`),
  CONSTRAINT `idUsuarioFK` FOREIGN KEY (`idUsuarioFK`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajadores`
--

LOCK TABLES `trabajadores` WRITE;
/*!40000 ALTER TABLE `trabajadores` DISABLE KEYS */;
INSERT INTO `trabajadores` VALUES (1,1);
/*!40000 ALTER TABLE `trabajadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombreUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `apellidosUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `telefonoUsuario` varchar(9) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `emailUsuario` varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `claveUsuario` varchar(256) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `emailUsuario_UNIQUE` (`emailUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Inma','Rivera','675824033','inma.rivera94@gmail.com','b283f6c032fb6491715425ddc684e23900740ab0f8ea11222105fec6a34f6909'),(2,'Antonia','Castro','674892003','antonia@gmail.com','9b7a0f2e159a5d55c77b8a79af6c557a4e9b4be4ccee9ece4cd7dc254fdfcc4e'),(9,'Teresa','Hato','786983452','teresa@gmail.com','9e88b067408182d0bd525120e9ba2607e19b0dfa17b48582d2d405fc52dc4a1a'),(11,'Alejandra','Martinez','675435676','fghg@gmail.com','069fca009882e13e01c6b0559c9b14a4337c4495f83fd720965ec80f0770a699'),(13,'Estefania','Rivas','779838593','estefania@gmail.com','f527721c54550e8a0a7aac8da3116976e0b8f3083d67b614d169e15f3df1a76a'),(15,'Paquita','Jaramillo','787394828','paquita@gmail.com','2c493a6c50d6e72e9125534b429a01e38930338332d41a6c74da4e17d19e964a'),(20,'Fernando','Martin','789098765','fernando@gmail.com','076a89c23179cedfc61171fe400ecf01fb76b9a48a68fb82dd0cd688d684d900'),(21,'María','Fernandez','675432563','maria@gmail.com','94aec9fbed989ece189a7e172c9cf41669050495152bc4c1dbf2a38d7fd85627'),(22,'Josefa','Rosendo','67485938','josefa@gmail.com','c50acbc0e7954cddb9360ad5318eed6b5042de601324ede17d8e7c5800a9e18c'),(25,'Manuela','Jimenez','765432536','manuela@gmail.com','72099cb2f0e789a71d57261a6c3d384ec6f119b4561d1169aebd1a3adbf11df4'),(26,'Pepa','Florindo','787658987','pepa@gmail.com','321cb76b7d6e43ef6917ee54fd8fa0e7fa99ba97c7e0b6fc7866139e7cb8c9c3'),(29,'Rosario','Perez','675483623','rosario@gmail.com','db26ce04fc0e235ae037a334d7e939ea6dedc4ff234fc5e5578fda274d578550'),(30,'Felipe','Suarez','765473847','felip@gmail.com','2bd2d3a31934d76198acc030caca4c31965474fe5fa48f35fef79d0fd74ee1b2'),(34,'Carmen','Trujillo','763849384','carmen@gmail.com','f3c2ce176290b0c384cb4881eb714f2db58f630c33863d91c9bedf58d36007db'),(38,'Mari Carmen','Rodriguez','674837493','mari@gmail.com','13203fa2ec7515f45338f825eedb64e9aa27128b506ec19c58ee742b0486c6b5');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-17  6:09:26
