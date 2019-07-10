
 SET NAMES utf8mb4 ;

DROP TABLE IF EXISTS `playlists`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `playlists` (
  `idPlaylist` int(11) NOT NULL,
  `idSong` int(11) DEFAULT NULL
);

LOCK TABLES `playlists` WRITE;

INSERT INTO `playlists` VALUES (1,2),(1,3),(1,4),(2,3),(2,4),(2,5),(3,1),(3,2),(3,4),(4,2),(4,6),(4,5),(5,1),(5,3),(5,2);

UNLOCK TABLES;
