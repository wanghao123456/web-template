create table photo
(
  id   bigint       not null auto_increment comment '主键',
  name varchar(64)  not null default '' comment '名称',
  url  varchar(128) not null default '' comment '路径',
  primary key (id)
);

create table user
(
  id        int          not null auto_increment comment '主键',
  user_name varchar(32)  not null default '' comment '用户名',
  pass_word varchar(128) not null default '' comment '密码',
  primary key (id)
);


create table role
(
  id        int         not null auto_increment comment '主键',
  role_name varchar(32) not null default '' comment '角色名称',
  primary key (id)
);


create table user_role
(
  user_id int not null comment 'user表主键',
  role_id int not null comment 'role表主键'
);