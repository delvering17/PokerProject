-- poker_db.`member` definition

CREATE TABLE `member` (
  `membernum` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `findpasswordQ` varchar(100) DEFAULT NULL,
  `findpasswordA` varchar(100) DEFAULT NULL,
  `signdate` date DEFAULT NULL,
  PRIMARY KEY (`membernum`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

-- poker_db.profile definition

CREATE TABLE `profile` (
  `profilenum` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `introduce` varchar(200) DEFAULT NULL,
  `totalGame` int(11) DEFAULT NULL,
  `win` int(11) DEFAULT NULL,
  `lose` int(11) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`profilenum`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;