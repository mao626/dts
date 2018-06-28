CREATE TABLE DTS_TRANSACTION (
	`txid` varchar(128) not null comment '事务id',
	`businessType` varchar(64) not null comment '业务类型',
	`businessId` varchar(128) not null comment '业务id',
	`status` tinyint(4) not null default 0 comment '状态，0-初始，1-事务提交',
	`createTime` bigint(20) not null comment '创建时间',
	`updateTime` bigint(20) not null comment '更新时间',
	primary key (`txid`),
	unique key `id_bussiness` (`businessType`, `businessId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment = 'dts事务表';

CREATE TABLE DTS_PARTICIPANT (
  `txid` varchar(128) not null comment '事务id',
  `name` varchar(64) DEFAULT '' comment '参与者业务名称',
  `status` tinyint(4) not null default 0 comment '状态，0-初始，1-事务提交',
  `context` varchar(1024) not null comment '事务参与者上下文环境',
  `createTime` bigint(20) not null comment '创建时间',
  `updateTime` bigint(20) not null comment '更新时间',
  key `id_txid` (`txid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='事务参与者表';