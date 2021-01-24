DROP TABLE IF EXISTS `shared_image`;
DROP TABLE IF EXISTS `phone_number`;
DROP TABLE IF EXISTS `image`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `approved` boolean default false,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);

CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(200) NOT NULL,
  `fk_user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_image` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `phone_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(25) NOT NULL,
  `otp` varchar(4) NOT NULL,
  `time_otp_added` TIMESTAMP NOT NULL,
  `approved` boolean default false,
  `fk_user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_phone` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `shared_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user` int(11) NOT NULL,
  `fk_image` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_image` FOREIGN KEY (`fk_image`) REFERENCES `image` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

