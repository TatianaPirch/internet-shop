CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `password` varchar(245) NOT NULL,
  `login` varchar(45) NOT NULL,
  `token` varchar(45) DEFAULT NULL,
  `salt` blob NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;


CREATE TABLE `roles` (
  `role_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('1', 'USER');
INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('2', 'ADMIN');

CREATE TABLE `users_roles` (
  `users_roles_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`users_roles_id`),
  KEY `users_roles_users_fk_idx` (`user_id`),
  KEY `users_roles_roles_fk_idx` (`role_id`),
  CONSTRAINT `users_roles_roles_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
   ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_roles_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `items` (
  `item_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(6,2) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('pen', '10');
INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('folder', '50');
INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('notebook', '100');

CREATE TABLE `buckets` (
  `bucket_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`bucket_id`),
  KEY `buckets_users_fk_idx` (`user_id`),
  CONSTRAINT `buckets_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
   ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;


CREATE TABLE `buckets_items` (
  `buckets_items_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bucket_id` BIGINT(20) NOT NULL,
  `item_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`buckets_items_id`),
  KEY `buckets_items_bucket_fk_idx` (`bucket_id`),
  KEY `buckets_items_items_fk_idx` (`item_id`),
  CONSTRAINT `buckets_items_bucket_fk` FOREIGN KEY (`bucket_id`) REFERENCES `buckets` (`bucket_id`)
   ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `buckets_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`)
  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8;


CREATE TABLE `orders` (
  `order_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `orders_users_fk_idx` (`user_id`),
  CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
   ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;


CREATE TABLE `orders_items` (
  `orders_items_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT(20) NOT NULL,
  `item_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`orders_items_id`),
  KEY `orders_items_orders_fk_idx` (`order_id`),
  KEY `orders_items_items_fk_idx` (`item_id`),
  CONSTRAINT `orders_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`)
   ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_items_orders_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
   ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
