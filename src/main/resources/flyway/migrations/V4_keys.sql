

ALTER TABLE `airportdb`.`airplane_route`
ADD INDEX `routes_id_idx` (`routes_id` ASC) VISIBLE;
;
ALTER TABLE `airportdb`.`airplane_route`
ADD CONSTRAINT `routes_id`
  FOREIGN KEY (`routes_id`)
  REFERENCES `airportdb`.`route` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `airportdb`.`user`
ADD CONSTRAINT `role_id`
  FOREIGN KEY (`role_id`)
  REFERENCES `airportdb`.`role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `airportdb`.`ticket`
ADD INDEX `order_id_idx` (`order_id` ASC) VISIBLE;
;
ALTER TABLE `airportdb`.`ticket`
ADD CONSTRAINT `order_id`
  FOREIGN KEY (`order_id`)
  REFERENCES `airportdb`.`order` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `airportdb`.`order`
ADD INDEX `user_id_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `airportdb`.`order`
ADD CONSTRAINT `user_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `airportdb`.`user` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `airportdb`.`order`
ADD INDEX `route_id_idx` (`route_id` ASC) VISIBLE;
;
ALTER TABLE `airportdb`.`order`
ADD CONSTRAINT `route_id`
  FOREIGN KEY (`route_id`)
  REFERENCES `airportdb`.`route` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `airportdb`.`airplane_route`
ADD INDEX `airplane_id_idx` (`airplane_id` ASC) VISIBLE;
;
ALTER TABLE `airportdb`.`airplane_route`
ADD CONSTRAINT `airplane_id`
  FOREIGN KEY (`airplane_id`)
  REFERENCES `airportdb`.`airplane` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
