drop table if exists t_absent_reason;
drop table if exists t_absence_act;

create table t_absent_reason
(
    id   int primary key generated by default as identity,
    name varchar(100) not null unique
);


create table t_absence_act
(
    id          bigint primary key generated by default as identity,
    reason_id   int references t_absent_reason (id) on delete set null,
    start_date  date                           not null,
    duration    integer check ( duration > 0 ) not null,
    discounted  bool                           not null,
    description varchar(1024)
);

INSERT INTO t_absent_reason(name)
values ('������'),
       ('����������'),
       ('������');

INSERT INTO t_absence_act(reason_id, start_date, duration, discounted, description)
VALUES (1, '2023-09-04', 7, true, '��������� ������������ ������'),
       (2, '2023-08-30', 1, false, '���������� �� ����� �� �������'),
       (3, '2023-08-20', 1, true, '��������� �� ������ �� ������');