create table users
(
    id          varchar(255) not null
        primary key,
    create_date date,
    edit_date   date,
    email       varchar(255),
    full_name   varchar(255),
    password    varchar(255),
    role        varchar(255)
);

alter table users
    owner to hits;

create table comments
(
    id          varchar(255) not null
        primary key,
    create_date date,
    edit_date   date,
    text        varchar(255),
    user_id     varchar(255)
        constraint user_id_constraint
            references users
);

alter table comments
    owner to hits;

create table projects
(
    id          varchar(255) not null
        primary key,
    create_date date,
    description varchar(255),
    edit_date   date,
    name        varchar(255)
);

alter table projects
    owner to hits;

create table tasks
(
    id            varchar(255) not null
        primary key,
    create_date   date,
    description   varchar(255),
    edit_date     date,
    priority      varchar(255),
    time_estimate timestamp,
    title         varchar(255),
    creator_id    varchar(255)
        constraint creator_id_constraint
            references users,
    executor_id   varchar(255)
        constraint executor_id_constraint
            references users,
    project_id    varchar(255)
        constraint project_id_constraint
            references projects
);

alter table tasks
    owner to hits;

create table comment_task
(
    comment_id varchar(255) not null
        constraint comment_id_constraint
            references comments,
    task_id    varchar(255) not null
        constraint task_id_constraint
            references tasks
);

alter table comment_task
    owner to hits;

