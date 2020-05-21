CREATE TABLE `t_msg` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `msg` varchar(255) DEFAULT NULL,
                         `create_time` datetime DEFAULT NULL,
                         `update_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;