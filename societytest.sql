-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 06, 2015 at 05:14 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `androidhive`
--

-- --------------------------------------------------------

--
-- Table structure for table `societytest`
--

CREATE TABLE IF NOT EXISTS `societytest` (
  `ID` int(11) NOT NULL,
  `Society` varchar(100) NOT NULL,
  `ContactInfo` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  FULLTEXT KEY `Society` (`Society`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `societytest`
--

INSERT INTO `societytest` (`ID`, `Society`, `ContactInfo`) VALUES
(1, 'AIESEC', 'angela.antoniou2@mail.dcu.ie'),
(2, 'Accounting and Finance', 'afdusoc@gmail.com'),
(3, 'Africa', 'dcuafricasoc@gmail.com'),
(4, 'Airsoft', 'airsoftdcu@gmail.com'),
(5, 'Amnesty International', 'http://www.amnestyinternational.ie'),
(6, 'An Cumann Gaelach', 'cumanngaelachdcu@gmail.com'),
(7, 'An Taisce', 'NO INFO'),
(8, 'Anime and Manga', 'animeandmanga@redbrick.dcu.ie'),
(9, 'Art Soc', 'artsocdcu@gmail.com'),
(10, 'Aussie Rules', 'dcuaussierules@gmail.com'),
(11, 'Biological Research', 'brs.dcu@gmail.com'),
(12, 'Book', 'booksoc@gmail.com'),
(13, 'Business Consulting', 'dcuconsulting@gmail.com'),
(14, 'Cancer	', 'NO INFO'),
(15, 'Chess Soc', 'dcuchess@gmail.com'),
(16, 'Chinese', 'qing.son3@mail.dcu.ie'),
(17, 'Christian Union', 'dcu-cu@hotmail.com'),
(18, 'Circus Arts', 'dcucircusarts@gmail.com'),
(19, 'DJ', 'dcudjsoc@gmail.com'),
(20, 'Dance', 'dcudance@gmail.com'),
(21, 'Debate', 'dcudebatesociety@gmail.com'),
(22, 'Drama', 'dcudramasoc@gmail.com'),
(23, 'Enactus', 'enactusdcu@gmail.com'),
(24, 'Engineering', 'engsoc-committee@lists.redbrick.dcu.ie'),
(25, 'Esoc', 'esocdcu@gmail.com'),
(26, 'FLAC', 'flacdcu@gmail.com'),
(27, 'FemSoc', 'NO INFO'),
(28, 'Fianna Fail', 'fiannafaildcu@gmail.com'),
(29, 'Film', 'NO INFO'),
(30, 'Financial & Actuarial', 'finact@redbrick.dcu.ie'),
(31, 'Food', 'foodsoc@redbrick.dcu.ie'),
(32, 'Fotosoc', 'fotosocdcu@gmail.com'),
(33, 'French', 'NO INFO'),
(34, 'Friends', 'friendssocdcu@gmail.com'),
(35, 'Gaisce', 'dcugaiscesoc@gmail.com'),
(36, 'Games', 'games@redbrick.dcu.ie'),
(37, 'Global Brigades', 'dcu@globalbrigades.org'),
(38, 'Global Business', 'globalbusinessdcu@gmail.com'),
(39, 'Hiking', 'dcuhiking@gmail.com'),
(40, 'Horticulture', 'horticulturedcu@gmail.com'),
(41, 'INTERNATIONAL RELATIONS', 'dcu.mun1@gmail.com'),
(42, 'Indian Soc', 'dcuindsoc@gmail.com'),
(43, 'Intercultural', 'dcuintersoc@gmail.com'),
(44, 'Investment', 'dcu.investmentsociety@gmail.com'),
(45, 'Islamic', 'dcuislamicsoc@gmail.com'),
(46, 'Japanese', 'japsoc2@gmail.com'),
(47, 'Journalism', 'dcujournalism@gmail.com'),
(48, 'Karting', 'dcukartsoc@gmail.com'),
(49, 'LGBTA', 'dculgbt@gmail.com'),
(50, 'Labour', 'labourdcu@gmail.com'),
(51, 'Law', 'dculawsociety@gmail.com'),
(52, 'Mature Students', 'matsoc@dcu.ie'),
(53, 'Media Production Society', 'info@dcumps.com'),
(54, 'Music', 'dcumusic@gmail.com'),
(55, 'Nursing', 'dcunursingsociety@gmail.com'),
(56, 'Paintball', 'dcupaintballsociety@gmail.com'),
(57, 'Poker', 'dcupokersoc.ie'),
(58, 'Pool & Snooker', 'max.thiemann2@mail.dcu.ie'),
(59, 'Postgrad', 'NO INFO'),
(60, 'Psychology', 'dcu.psych.soc@gmail.com'),
(61, 'RAW - Raising for Animal Welfare', 'dcuraw@gmail.com'),
(62, 'Raising and Giving', 'dcurag@gmail.com'),
(63, 'Redbrick: Networking Society', 'committee@redbrick.dcu.ie'),
(64, 'Sinn Fein', 'dcusinnfein32@gmail.com'),
(65, 'Snowsports', 'dcu.snowsports@gmail.com'),
(66, 'Sober Society', 'sobersoc@gmail.com'),
(67, 'Society Of Science', 'dcusos@gmail.com'),
(68, 'St Vincent De Paul', 'dcusvp@gmail.com'),
(69, 'Strange Things', 'strangethingsoc@gmail.com'),
(70, 'Student Business Consulting', 'dcuconsulting@gmail.com'),
(71, 'Style', 'chairperson@dcustylesociety.com'),
(72, 'Suas', 'dcu.suassoc@gmail.com'),
(73, 'Tea Soc', 'dcuteasoc@gmail.com'),
(74, 'Unicef on Campus', 'unicefoncampusdcu@gmail.com'),
(75, 'Urban Artz', 'urbanartzdcu@gmail.com'),
(76, 'Walk and Talk', 'dcuwats@gmail.com'),
(77, 'Yoga', 'dcuyoga@gmail.com'),
(78, 'Young Fine Gael', 'yfgdcu@gmail.com'),
(79, 'Aikido', 'NO INFO'),
(80, 'Archery', 'secretary.dcuac@gmail.com'),
(81, 'Athletics', 'NO INFO'),
(82, 'Badminton', 'dcushuttleclub@gmail.com'),
(83, 'Basketball Men', 'dcumensbasketball@gmail.com'),
(84, 'Basketball Women', 'dculadiesbasketball@gmail.com'),
(85, 'Boxing Club', 'boxingamateur.dcu@mail.com'),
(86, 'Camogie', 'aislinn.sheehan5@mail.dcu.ie'),
(87, 'Canoe', 'andrew.donohoe23@mail.dcu.ie'),
(88, 'Caving', 'dcucavingclub@gmail.com'),
(89, 'Clay Target', 'dcuctc@gmail.com'),
(90, 'Cricket', 'cricetclubdcu@gmail.com'),
(91, 'Cycling', 'dcucycling@gmail.com'),
(92, 'Equestrian', 'equestrian.dcu@gmail.com'),
(93, 'Fencing', 'dcufencing@gmail.com'),
(94, 'GAA (M)', 'admin.dcu@gaa.ie'),
(95, 'GAA (W)', 'brid.osullivan25@mail.dcu.ie'),
(96, 'Golf', 'amy.farrell58@mail.dcu.ie'),
(97, 'Gymnastics & Trampolining', 'dcutrampclub@gmail.com'),
(98, 'Handball', 'dcuhandball@gmail.com'),
(99, 'Hockey (Men & Womens)', 'hayley.manning2@mail.dcu.ie'),
(100, 'Horse Racing', 'dcuhorseracing@gmail.com'),
(101, 'Hurling', 'dcuhurlingclub@gmail.com'),
(102, 'Jogging', 'dcujogging@gmail.com'),
(103, 'Judo', 'dcudojo@gmail.com'),
(104, 'Karate', 'DCUKarateinfo@gmail.com'),
(105, 'Mixed Martial Arts', 'florin.frosin2@mail.dcu.ie'),
(106, 'Rock Climbing', 'climbing@redbrick.dcu.ie'),
(107, 'Rowing', 'dcu.rowing@gmail.com'),
(108, 'Rugby (Men)', 'dcurfc@gmail.com'),
(109, 'Rugby (Women)', 'dculadiesrugby@gmail.com'),
(110, 'Soccer (Men)', 'soccer@dcu.ie'),
(111, 'Soccer (Women)', 'dculadiessoccerclub@gmail.com'),
(112, 'Squash', 'dcusquash@gmail.com'),
(113, 'Sub Aqua', 'dcusubaquaclub@gmail.com'),
(114, 'Surf n Sail', 'dcusurfnsailclub@gmail.com'),
(115, 'Swimming/Waterpolo', 'dcuswimming@gmail.com'),
(116, 'Table Tennis', 'dcutabletennisclub@gmail.com'),
(117, 'Taekwon Do', 'dcutkd@gmail.com'),
(118, 'Tennis', 'dcutennis@yahoo.com'),
(119, 'Ultimate Frisbee', 'dcuultimate37@gmail.com'),
(120, 'Volleyball', 'dcu.volley@gmail.com'),
(121, 'Weight Lifting', 'dcuweightlifting@gmail.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
