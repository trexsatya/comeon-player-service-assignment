create table if not exists
    players
(
    id            serial primary key,
    name          text not null,
    email         text not null,
    password      text not null,
    surname       text not null,
    date_of_birth date not null,
    address       text not null,
    daily_limit   int  not null,
    created_at    timestamp default current_timestamp
);

create table if not exists sessions
(
    id         uuid primary key,
    player_id  int not null,
    created_at timestamp default current_timestamp,
    last_login_at timestamp,
    last_logout_at timestamp,
    active_time_secs int not null,
    constraint FK_player_id foreign key (player_id) references players (id)
);