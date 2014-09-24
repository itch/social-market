-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Час створення: Вер 22 2014 р., 22:31
-- Версія сервера: 5.6.16
-- Версія PHP: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База даних: `social_market`
--

-- --------------------------------------------------------

--
-- Структура таблиці `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) DEFAULT '0',
  `children_ORDER` int(11) DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=57 ;

--
-- Дамп даних таблиці `category`
--

INSERT INTO `category` (`id`, `name`, `parent_id`, `children_ORDER`, `user_id`) VALUES
(3, 'root', 0, 0, 1),
(5, 'sub2', 3, 0, 1),
(6, 'sub3', 3, 0, 1),
(7, 'sub sub3', 6, 0, 1),
(8, 'sub12', 6, 0, 1),
(28, 'New sub category3', 6, 0, 1),
(34, 'test', 5, 0, 1),
(36, 'New sub categorye344234', 5, 0, 1),
(18, 'sub22', 5, 0, 1),
(29, 'New sub category2', 6, 0, 1),
(21, '123', 6, 0, 1),
(32, 'sub222', 18, 0, 1),
(35, 'New sub category', 5, 0, 1),
(26, 'New sub category', 5, 0, 1),
(37, 'New sub category', 34, 0, 1),
(38, 'ss', 6, 0, 1),
(39, 'New sub category', 7, 0, 1),
(40, 'New sub category', 34, 0, 1),
(41, 'New sub category', 34, 0, 1),
(42, 'New sub category', 26, 0, 1),
(43, 'New sub categoryty', 21, 0, 1),
(52, 'rwar', 3, 0, 1),
(53, 'user666', 0, 0, 2),
(54, 'some catefory', 53, 0, 2),
(55, 'tset', 54, 0, 2),
(56, 'New root category45345345', 3, 0, 1);

-- --------------------------------------------------------

--
-- Структура таблиці `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Дамп даних таблиці `product`
--

INSERT INTO `product` (`id`, `name`, `user_id`) VALUES
(1, 'product 233', 1),
(2, 'test', 1),
(4, 'user product', 1),
(5, 'admin product', 1),
(6, 'user product', 2),
(7, 'teet', 1);

-- --------------------------------------------------------

--
-- Структура таблиці `product_category`
--

CREATE TABLE IF NOT EXISTS `product_category` (
  `Categories_id` int(11) NOT NULL,
  `Product_id` int(11) NOT NULL,
  KEY `Category_id` (`Categories_id`,`Product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп даних таблиці `product_category`
--

INSERT INTO `product_category` (`Categories_id`, `Product_id`) VALUES
(3, 1),
(5, 4),
(5, 5),
(6, 2),
(6, 4),
(6, 5),
(7, 4),
(7, 5),
(8, 5),
(42, 2),
(43, 2),
(53, 6),
(54, 6),
(55, 6);

-- --------------------------------------------------------

--
-- Структура таблиці `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(100) NOT NULL,
  `roledesc` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Дамп даних таблиці `role`
--

INSERT INTO `role` (`id`, `rolename`, `roledesc`) VALUES
(1, 'Administrators', 'Administrator'),
(2, 'Users', 'User');

-- --------------------------------------------------------

--
-- Структура таблиці `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Дамп даних таблиці `user`
--

INSERT INTO `user` (`id`, `login`, `name`, `password`, `role_id`) VALUES
(1, 'usr', 'usr33', 'usr', 2),
(2, 'user', 'user_1', 'user', 2),
(14, 'admin', 'name_0.8570286831482302', 'admin', 1);

-- --------------------------------------------------------

--
-- Структура таблиці `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `Role_roleid` int(11) NOT NULL,
  `User_userid` int(11) NOT NULL,
  KEY `Role_roleid` (`Role_roleid`,`User_userid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Дамп даних таблиці `user_roles`
--

INSERT INTO `user_roles` (`Role_roleid`, `User_userid`) VALUES
(1, 1),
(1, 9);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
