create table customer(
	num number(3),
	name varchar2(10),
	email varchar2(20),
	tel varchar2(15)
);

insert into customer values(1, '홍길동', 'abc1@abc.com', '010-1234-5234');
insert into customer values(2, '홍길서', 'abc2@abc.com', '010-2234-4234');
insert into customer values(3, '홍길남', 'abc3@abc.com', '010-3234-3234');
insert into customer values(4, '홍길북', 'abc4@abc.com', '010-4234-2234');
insert into customer values(5, '아무개', 'abc5@abc.com', '010-5234-1234');

	
select * from customer;	

insert into CUSTOMER(sal) values ('200')


// 칼럼명 변경 및 삭제등
alter table customer drop column sal
alter table customer nologging
alter table customer add(sal varchar2(300))	
alter table customer rename column to namechange	


select ascII('a'), substr('abc',1,2), length('a bc'), ltrim(' abc'), length(ltrim(' abc')) from dual
select sysdate, extract(year from sysdate), to_char(sysdate, 'yyyymmdd') from dual	
	
select decode (name, '홍길동', 'true', 'false') from customer	
select case when num = 1 then 'A'
				when num =2 then 'B'
				else 'c'
				end
				from customer
	
	
	
	
	
	
	
	