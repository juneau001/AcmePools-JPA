-- *****************************************************
-- * Simple database model for AcmePools application.
-- * 
-- * Author:  J. Juneau
-- * Description:  Ready for summer!  Run this SQL in
-- *   your favorite Apache derby schema
-- *****************************************************


create table job (
    id              int primary key,
    customer_id     int,
    description     clob,
    est_hours       float,
    cost            numeric);



create table pool (
    id              int primary key,
    style           varchar(10),
    shape           varchar(10),
    length          double,
    width           double,
    radius          double,
    gallons         double,
    shallow_depth   double,
    deep_depth      double);

alter table customer
add current_maintenance varchar(1);

alter table customer
add column pool_id int;

alter table customer
add constraint pool_id_fk
foreign key (pool_id) references pool(id);

-- Add support for data export
create table column_model(
id                  int primary key,
column_name         varchar(30),
column_label        varchar(150));


insert into column_model values(
1,
'addressline1',
'Address Line 1');

insert into column_model values(
2,
'addressline2',
'Address Line 2');

insert into column_model values(
3,
'city',
'City');

insert into column_model values(
4,
'creditLimit',
'Credit Limit');

insert into column_model values(
5,
'customerId',
'Customer Id');

insert into column_model values(
6,
'discountCode',
'Discount Code');

insert into column_model values(
7,
'email',
'Email');

insert into column_model values(
8,
'fax',
'Fax');

insert into column_model values(
9,
'name',
'Name');

insert into column_model values(
10,
'phone',
'Phone');

insert into column_model values(
11,
'state',
'State');

insert into column_model values(
12,
'zip',
'Zip');




insert into pool values(
1,
'ABOVE',
'ROUND',
0,
0,
24,
27143.0,
4,
0);

insert into pool values(
2,
'INGROUND',
'RECTANGLE',
32,
16,
0,
21120.0,
3,
8);

update customer
set current_maintenance = 'Y';

update customer
set pool_id = 1
where credit_limit <= 25000;

update customer
set pool_id = 2
where credit_limit > 25000;