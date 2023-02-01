-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 01, 2023 at 03:31 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ngetehyuk`
--

-- --------------------------------------------------------

--
-- Table structure for table `milktea`
--

CREATE TABLE `milktea` (
  `DrinkID` varchar(6) NOT NULL,
  `DrinkName` varchar(50) NOT NULL,
  `DrinkType` varchar(20) NOT NULL,
  `DrinkPrice` int(11) NOT NULL,
  `MilkType` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `milktea`
--

INSERT INTO `milktea` (`DrinkID`, `DrinkName`, `DrinkType`, `DrinkPrice`, `MilkType`) VALUES
('MT001', 'Teazy Pot', 'Milk Tea', 35000, 'Toned Milk'),
('MT002', 'Chai Talker', 'Milk Tea', 44000, 'Full Cream Milk'),
('MT003', 'Cuppy Cow', 'Milk Tea', 32000, 'Standardised Milk'),
('MT004', 'Milk Drop', 'Milk Tea', 48000, 'Skimmed Milk'),
('MT005', 'Rose Honeydew', 'Milk Tea', 55000, 'Recombined Milk');

-- --------------------------------------------------------

--
-- Table structure for table `tea`
--

CREATE TABLE `tea` (
  `DrinkID` varchar(6) NOT NULL,
  `DrinkName` varchar(50) NOT NULL,
  `DrinkType` varchar(20) NOT NULL,
  `DrinkPrice` int(11) NOT NULL,
  `SugarType` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tea`
--

INSERT INTO `tea` (`DrinkID`, `DrinkName`, `DrinkType`, `DrinkPrice`, `SugarType`) VALUES
('TE001', 'Oolong Tea', 'Tea', 15000, 'Cane Sugar'),
('TE002', 'Pu-erh', 'Tea', 20000, 'Coarse Sugar'),
('TE003', 'Yerba Mate', 'Tea', 22000, 'Fruit Sugar'),
('TE004', 'Guayusa', 'Tea', 28000, 'Sanding Sugar'),
('TE005', 'Herbal Tea', 'Tea', 35000, 'Powdered Sugar');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `TransactionID` varchar(6) NOT NULL,
  `DrinkID` varchar(6) NOT NULL,
  `BuyerName` varchar(35) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionID`, `DrinkID`, `BuyerName`, `Quantity`) VALUES
('TR003', 'TE001', 'Kodai Naraoka', 3),
('TR005', 'MT002', 'Kim Seungmin', 2),
('TR006', 'TE005', 'Akane Yamaguchi', 3),
('TR007', 'TE002', 'Bang Chan', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `milktea`
--
ALTER TABLE `milktea`
  ADD PRIMARY KEY (`DrinkID`);

--
-- Indexes for table `tea`
--
ALTER TABLE `tea`
  ADD PRIMARY KEY (`DrinkID`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`TransactionID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
