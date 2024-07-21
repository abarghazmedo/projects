-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2024 at 06:25 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `PermissionID` int(11) NOT NULL,
  `TaskAppName` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`PermissionID`, `TaskAppName`) VALUES
(7, 'Dashbord view'),
(8, 'Dashbord edit'),
(9, 'Dashbord hide'),
(10, 'Project view'),
(11, 'Project edit'),
(12, 'Project hide'),
(13, 'Task view'),
(14, 'Task edit'),
(15, 'Task hide'),
(16, 'SubTask view'),
(17, 'SubTask edit'),
(18, 'SubTask hide'),
(19, 'User view'),
(20, 'User edit'),
(21, 'User hide'),
(22, 'Role view'),
(23, 'Role edit'),
(24, 'Role hide');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `projectID` int(11) NOT NULL,
  `projectName` varchar(225) DEFAULT NULL,
  `projectDescription` varchar(225) DEFAULT NULL,
  `Startdate` date DEFAULT NULL,
  `ENDdate` date DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `RoleID` int(11) NOT NULL,
  `roleName` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`RoleID`, `roleName`) VALUES
(2, 'Manager'),
(4, 'Delppoer'),
(6, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `subtask`
--

CREATE TABLE `subtask` (
  `subtaskID` int(11) NOT NULL,
  `subtaskName` varchar(225) DEFAULT NULL,
  `subtaskDescription` varchar(300) DEFAULT NULL,
  `Estimer` int(150) DEFAULT NULL,
  `subtaskPriority` varchar(30) DEFAULT NULL,
  `subtaskStatut` varchar(30) DEFAULT NULL,
  `subtaskType` varchar(30) DEFAULT NULL,
  `taskID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `taskID` int(11) NOT NULL,
  `taskName` varchar(225) DEFAULT NULL,
  `taskDescription` varchar(300) DEFAULT NULL,
  `Estimer` int(150) DEFAULT NULL,
  `taskPriority` varchar(30) DEFAULT NULL,
  `taskStatut` varchar(30) DEFAULT NULL,
  `taskType` varchar(30) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `projectID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `userp`
--

CREATE TABLE `userp` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(225) DEFAULT NULL,
  `UserEmail` varchar(225) DEFAULT NULL,
  `UserJob` varchar(225) DEFAULT NULL,
  `UserPassword` varchar(225) DEFAULT NULL,
  `RoleID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userp`
--

INSERT INTO `userp` (`UserID`, `UserName`, `UserEmail`, `UserJob`, `UserPassword`, `RoleID`) VALUES
(41, 'sdgqsd', 'md@gmail.com', 'defqsdqf', '123456', 6),
(42, 'ffgfg', 'ttt', 'ffgfgfg', 'ttt', 2),
(43, 'ffg', 'aaa', 'feef', 'aaa', 6);

-- --------------------------------------------------------

--
-- Table structure for table `userpermission`
--

CREATE TABLE `userpermission` (
  `UserID` int(11) DEFAULT NULL,
  `PermissionID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userpermission`
--

INSERT INTO `userpermission` (`UserID`, `PermissionID`) VALUES
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(42, 11),
(43, 23),
(43, 17),
(43, 15),
(43, 20),
(43, 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`PermissionID`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`projectID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indexes for table `subtask`
--
ALTER TABLE `subtask`
  ADD PRIMARY KEY (`subtaskID`),
  ADD KEY `taskID` (`taskID`),
  ADD KEY `subtask_ibfk_1` (`userID`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`taskID`),
  ADD KEY `userID` (`userID`),
  ADD KEY `projectID` (`projectID`);

--
-- Indexes for table `userp`
--
ALTER TABLE `userp`
  ADD PRIMARY KEY (`UserID`),
  ADD KEY `userp_ibfk_1` (`RoleID`);

--
-- Indexes for table `userpermission`
--
ALTER TABLE `userpermission`
  ADD KEY `UserID` (`UserID`),
  ADD KEY `AppID` (`PermissionID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `PermissionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `projectID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `RoleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `subtask`
--
ALTER TABLE `subtask`
  MODIFY `subtaskID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `taskID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `userp`
--
ALTER TABLE `userp`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `project_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `userp` (`UserID`);

--
-- Constraints for table `subtask`
--
ALTER TABLE `subtask`
  ADD CONSTRAINT `subtask_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `userp` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `subtask_ibfk_2` FOREIGN KEY (`taskID`) REFERENCES `task` (`taskID`);

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `userp` (`UserID`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`projectID`) REFERENCES `project` (`projectID`);

--
-- Constraints for table `userp`
--
ALTER TABLE `userp`
  ADD CONSTRAINT `userp_ibfk_1` FOREIGN KEY (`RoleID`) REFERENCES `role` (`RoleID`) ON DELETE CASCADE;

--
-- Constraints for table `userpermission`
--
ALTER TABLE `userpermission`
  ADD CONSTRAINT `userpermission_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `userp` (`UserID`),
  ADD CONSTRAINT `userpermission_ibfk_2` FOREIGN KEY (`PermissionID`) REFERENCES `permission` (`PermissionID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
