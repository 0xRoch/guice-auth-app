# --- First database schema

# --- !Ups

create table user (
  id                        bigint not null primary key,
  username                  varchar(255) not null,
  password                  varchar(255) not null
);

# --- !Downs

drop table if exists user;