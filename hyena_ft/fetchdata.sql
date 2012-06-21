CREATE TABLE `army_news` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `cont_file` varchar(255),
  `createtime` datetime NOT NULL,
  resume TEXT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

