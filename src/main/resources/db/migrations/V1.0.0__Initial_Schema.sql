create table if not exists candlestick
(
    id         bigint auto_increment primary key,
    created_at date,
    ticker     varchar(20),
    high       double,
    low        double,
    open       double,
    close      double,
    volume     double,
    unique (ticker, created_at)
);