CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet_shop`.`items` (
`item_id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`price` DECIMAL(6,2) NOT NULL,
PRIMARY KEY (`item_id`));

INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('pen', '10');
INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('folder', '50');
INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('notebook', '100');