create table categories
(
    id         bigserial primary key,
    title      varchar(255) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories (title)
values ('Food'),
       ('Other');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    category_id bigint references categories (id),
    price       int,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products (title, category_id, price)
values ('Milk', 1, 80),
       ('Bread', 1, 25),
       ('Cheese', 1, 300);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price int          not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);
