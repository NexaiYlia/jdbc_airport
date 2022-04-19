CREATE TABLE `airportdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `airportdb`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `airportdb`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL,
  `passport_data` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


ALTER TABLE `airportdb`.`user`
ADD COLUMN `passport` VARCHAR(255) NOT NULL AFTER `role_id`;

CREATE TABLE `airportdb`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NOT NULL,
  `order_date` DATE NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `airportdb`.`aircompany` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `airportdb`.`airplane` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(255) NOT NULL,
  `aircompany_id` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `airportdb`.`route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `departure_id` INT NOT NULL,
  `arrival_id` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `airportdb`.`airplane_route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `airplane_id` INT NULL,
  `routes_id` INT NULL,
  PRIMARY KEY (`id`));