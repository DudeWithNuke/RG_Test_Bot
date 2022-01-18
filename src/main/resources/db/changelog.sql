create table users
(
    id              bigserial primary key not null,
    chat_id         bigserial             not null unique,
    username        varchar(256)          not null unique,
    last_message_at timestamp             not null unique
);
--rollback drop table users;

create table pair_msg
(
    id          bigserial primary key not null,
    user_id     bigint references users,
    user_in_msg text                  not null,
    bot_out_msg text                  not null
);
--rollback drop table pair_msg;

create table daily_domains
(
    id          bigserial primary key not null,
    domainname  varchar(256),
    hotness     smallint,
    price       int,
    x_value     smallint,
    yandex_tic  int,
    links       int,
    visitors    int,
    registrar   varchar(256),
    old         smallint,
    delete_date timestamp,
    rkn         boolean,
    judicial    boolean,
    block       boolean
)
--rollback drop table daily_domains;