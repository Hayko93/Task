use task_test;

create table user
(
    id          int primary key auto_increment,
    first_name        varchar(255) not null,
    last_name        varchar(255) not null,
    email       varchar(255) not null unique,
    password    varchar(255) not null,
    active      tinyint default 0,
    active_code varchar(255)

);

create table role
(
    id   int primary key auto_increment,
    role varchar(50) unique
);


create table user_role
(
    user_id int not null,
    role_id int not null,
    constraint user_id_foreign_key foreign key (user_id) references user (id) on update cascade on delete cascade,
    constraint role_id_foreign_key foreign key (role_id) references role (id) on update cascade on delete cascade
);

create table board
(
    id                     int primary key auto_increment,
    title                  varchar(255) not null,
    admin_id               int          not null,
    constraint owner_user_id foreign key (admin_id) references user (id)
);

create table board_member
(
    id       int primary key auto_increment,
    user_id  int not null,
    board_id int not null,
    role_id  int not null,
    constraint user_id_board foreign key (user_id) references user (id) on update cascade on delete cascade,
    constraint board_id_user foreign key (board_id) references board (id) on delete cascade on update cascade,
    constraint role_id_user foreign key (role_id) references role (id) on update cascade on delete cascade
);

create unique index board_members_uniq on board_member (user_id, board_id, role_id);

