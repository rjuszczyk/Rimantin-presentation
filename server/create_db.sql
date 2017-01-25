CREATE TABLE `rimantin_presentation_scores` (
  `ID` int(11) NOT NULL,
  `agent` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `pharmacy` varchar(255) DEFAULT NULL,
  `participantNumber` varchar(255) DEFAULT NULL,
  `timeSpendInApp` varchar(255) DEFAULT NULL,
  `serverDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createDate` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;