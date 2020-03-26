-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 26-03-2020 a las 13:59:28
-- Versión del servidor: 10.4.6-MariaDB
-- Versión de PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `productosapirest`
--
CREATE DATABASE IF NOT EXISTS `productosapirest` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `productosapirest`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(1, 'Comida'),
(2, 'Bebida'),
(3, 'Complementos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) UNSIGNED NOT NULL,
  `cycle_option` tinyint(1) UNSIGNED NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
(1000, 1, 9223372036854775806, 1000, 1, 1000, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_pedido`
--

DROP TABLE IF EXISTS `linea_pedido`;
CREATE TABLE `linea_pedido` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` float NOT NULL,
  `pedido_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `linea_pedido`
--

INSERT INTO `linea_pedido` (`id`, `cantidad`, `precio`, `pedido_id`, `producto_id`) VALUES
(1, 1, 91, 1, 1),
(2, 3, 87, 1, 2),
(3, 2, 77, 1, 29),
(4, 3, 72, 2, 19),
(5, 2, 19, 2, 28),
(6, 1, 25, 3, 9),
(7, 5, 38, 3, 13),
(8, 1, 72, 3, 22);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lote`
--

DROP TABLE IF EXISTS `lote`;
CREATE TABLE `lote` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `lote`
--

INSERT INTO `lote` (`id`, `nombre`) VALUES
(1, 'Lote 1'),
(2, 'Lote 2'),
(3, 'Lote 3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lote_productos`
--

DROP TABLE IF EXISTS `lote_productos`;
CREATE TABLE `lote_productos` (
  `lote_id` bigint(20) NOT NULL,
  `producto_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `lote_productos`
--

INSERT INTO `lote_productos` (`lote_id`, `producto_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(3, 21),
(3, 22),
(3, 23),
(3, 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE `pedido` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id`, `fecha`, `cliente_id`) VALUES
(1, '2020-03-26 13:58:59', 2),
(2, '2020-03-26 13:58:59', 3),
(3, '2020-03-26 13:58:59', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL,
  `imagen` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `precio` float NOT NULL,
  `categoria_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `imagen`, `nombre`, `precio`, `categoria_id`) VALUES
(1, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Juice - Orange, Concentrate', 91, 2),
(2, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Beef - Ground, Extra Lean, Fresh', 87, 1),
(3, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Cheese - Parmesan Grated', 39, 1),
(4, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Cups 10oz Trans', 67, 1),
(5, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Wine - Beringer Founders Estate', 27, 2),
(6, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Bread - Wheat Baguette', 82, 2),
(7, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Quail - Eggs, Fresh', 3, 2),
(8, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Cheese - Mascarpone', 97, 2),
(9, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Mace', 25, 2),
(10, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Oil - Shortening - All - Purpose', 63, 2),
(11, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Marjoram - Fresh', 60, 2),
(12, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Turnip - White', 74, 2),
(13, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Pork Salted Bellies', 38, 2),
(14, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Longos - Greek Salad', 15, 2),
(15, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Amaretto', 85, 2),
(16, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Godiva White Chocolate', 97, 2),
(17, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Tomatoes - Roma', 61, 2),
(18, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Oven Mitt - 13 Inch', 1, 3),
(19, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Vermouth - White, Cinzano', 72, 2),
(20, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Club Soda - Schweppes, 355 Ml', 38, 2),
(21, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Fenngreek Seed', 1, 1),
(22, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Dill Weed - Dry', 72, 1),
(23, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Pepper - Green', 56, 1),
(24, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Bacardi Breezer - Tropical', 35, 2),
(25, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Wine - Merlot Vina Carmen', 14, 2),
(26, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Sauce - Black Current, Dry Mix', 9, 1),
(27, 'http://dummyimage.com/133x134.bmp/dddddd/000000', 'Crab - Soft Shell', 17, 1),
(28, 'http://dummyimage.com/245x246.jpg/dddddd/000000', 'Jameson Irish Whiskey', 19, 2),
(29, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff', 'Muffin Chocolate Individual Wrap', 77, 1),
(30, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff', 'Mussels - Frozen', 95, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_entity_roles`
--

DROP TABLE IF EXISTS `user_entity_roles`;
CREATE TABLE `user_entity_roles` (
  `user_entity_id` bigint(20) NOT NULL,
  `roles` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `user_entity_roles`
--

INSERT INTO `user_entity_roles` (`user_entity_id`, `roles`) VALUES
(1, 'USER'),
(1, 'ADMIN'),
(2, 'USER'),
(3, 'USER'),
(4, 'USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `last_password_change_at` datetime DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `avatar`, `created_at`, `email`, `full_name`, `last_password_change_at`, `password`, `username`) VALUES
(1, 'https://api.adorable.io/avatars/285/admin@openwebinars.net.png', '2020-03-26 13:58:59', 'admin@prueba.net', 'Admin admin', '2020-03-26 13:58:59', '$2a$10$vPaqZvZkz6jhb7U7k/V/v.5vprfNdOnh4sxi/qpPRkYTzPmFlI9p2', 'admin'),
(2, 'https://api.adorable.io/avatars/285/maria.lopez@openwebinars.net.png', '2020-03-26 13:58:59', 'maria.lopez@prueba.net', 'María López', '2020-03-26 13:58:59', '$2a$10$3i95KIxdl8igcpDby.URMOgwdDR2q9UaSfor2kJJrhAPfNOC/HMSi', 'marialopez'),
(3, 'https://api.adorable.io/avatars/285/angel.martinez@openwebinars.net.png', '2020-03-26 13:58:59', 'angel.martinez@prueba.net', 'Ángel Martínez', '2020-03-26 13:58:59', '$2a$10$37IEM6zzuwXqFrotYDtySOKITKfeNWR3NBRqcM7JYWnBDIaq9ByZm', 'angelmartinez'),
(4, 'https://api.adorable.io/avatars/285/ana.jimenez@openwebinars.net.png', '2020-03-26 13:58:59', 'ana.jimenez@prueba.net', 'Ana Jiménez', '2020-03-26 13:58:59', '$2a$10$k0om5gtNBheWX54VzD1E0etCnqC0xChHjfW3lOXaeCpN5ST1vVGYm', 'anajimenez');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `linea_pedido`
--
ALTER TABLE `linea_pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoi533vfp5jf0jgf9dws0s0pw4` (`pedido_id`),
  ADD KEY `FKhhtgctnq5gn29qdye9todv6ku` (`producto_id`);

--
-- Indices de la tabla `lote`
--
ALTER TABLE `lote`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `lote_productos`
--
ALTER TABLE `lote_productos`
  ADD PRIMARY KEY (`lote_id`,`producto_id`),
  ADD KEY `FK7imwdeety3x4lim4n6p84ac9y` (`producto_id`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnx8k5in15mc7c2otjufcfcvu8` (`cliente_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKodqr7965ok9rwquj1utiamt0m` (`categoria_id`);

--
-- Indices de la tabla `user_entity_roles`
--
ALTER TABLE `user_entity_roles`
  ADD KEY `FKl4xftbwcj3tpm12y7rmt2s606` (`user_entity_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_m2dvbwfge291euvmk6vkkocao` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `linea_pedido`
--
ALTER TABLE `linea_pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `lote`
--
ALTER TABLE `lote`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
