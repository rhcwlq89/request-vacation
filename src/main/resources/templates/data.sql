CREATE TABLE `member_m` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '회원아이디',
  `name` varchar(255) NOT NULL COMMENT '회원이름',
  `password` varchar(255) NOT NULL COMMENT '비밀번호',
  `authority` varchar(45) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='회원테이블';

CREATE TABLE `member_vacation_history` (
  `history_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '휴가신청ID',
  `member_vacation_id` bigint(20) NOT NULL,
  `request_status` varchar(6) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `vacation_days` decimal(4,2) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp(),
  `mod_dt` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `member_vacation_m` (
  `member_vacation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `vacation_year` varchar(4) NOT NULL,
  `total_count` decimal(4,2) NOT NULL,
  `use_count` decimal(4,2) NOT NULL,
  PRIMARY KEY (`member_vacation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;