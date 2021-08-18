-- 테이블의 수정(ALTER)

--1. 필드명의변경
-- ALTER TABLE 테이블이름 rename column 변경전 이름 to 변경후 이름;
alter table booklist rename column title to subject;
-- booklist 테이블의 title 필드 이름을 subject 로 수정하세요
select * from booklist;

​
--2. 필드 자료형의 변경
-- varchar2(12) 였던 person 테이블의 personname 필드를 varchar2(30)으로 변경
alter table person modify personname varchar2(30);
select * from person;


--3. 필드의 추가
-- ALTER TABLE 테이블명 ADD 필드명 자료형
-- booklist에 구매등급을 저장할 수 잇는 grade 필드를 varchar2(15)로 추가
alter table booklist add grade varchar2(15);
-- person 테이블에 성별(gender) 필드 추가varchar2(3)
alter table person add gender varchar2(3);
-- person 테이블에 나이(age) 필드 추가 number(2)
alter table person add age number(2);

select * from booklist;
select * from person;

​
--4. 필드의 삭제
-- ALTER TABLE 테이블명 DROP COLUMN 필드명
-- person 테이블에서 성별 필드 제거
alter table person drop column gender;
select * from person;

​
--5. 제약 조건의 추가/제거
alter table person add gender varchar2(3);

-- ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 제약조건식
-- person 테이블에서 성별 필드에 'F', 'M' 만입력되도록 제약조건 추가. 제약조건명 : check_gender
alter table person add constraint check_gender check( gender in('F','M') );

-- 나이 필드에 120 살이 초과 되는 나이는 입력되지 못하게 제약 조건 추가. 제약조건명 : check_age
alter table person add constraint check_age check( age<=120 );

-- 제약 조건의 제거
-- alter table 테이블명 drop constraint 제약조건명;

-- in_out 테이블에서 booklist 테이블의 booknum 을를 참조하는 외래키 제거
alter table in_out drop constraint fk1;

-- in_out에서 기본키 제거
alter table in_out drop constraint in_out_PK;

-- in_out에서 기본키 다시 추가(제거한 기본키와 같은 이름, 같은 필드 를 이용)
alter table in_out add constraint in_out_PK primary key (out_date, indexk);

-- in_out 테이블에서booklist테이블의 booknum 를 참조하는 외래키 다시 추가
alter table in_out add constraint fk1 foreign key (booknum)
references booklist(booknum);

-- booklist의 grade 필드에 '전체' '청소년구매가능','성인전용' 로 입력을 제한
alter table booklist add constraint check_grade 
check( grade in('전체', '청소년구매가능','성인전용') );

alter table booklist drop constraint check_grade;
select * from booklist;
-- 테이블 생성 연습
-- 테이블명 : ORDERS 
-- 필드 : ORDER_ID NUMBER(12,0) ORDER_DATE DATE 
-- ORDER_MODE VARCHAR2(8) CUSTOMER_ID NuMBER(6,0) 
-- ORDER_STATUS NUMBER(2,0) ORDER_TOTAL NUMBER(8,2) 
-- SALES_REP_ID NUMBER(6,0) PROMOTION_ID NUMBER(6,0) 
-- 제약사항 : 기본키는 ORDER_ID 
-- ORDER_MODE에는 'direct', 'online'만 입력가능 
-- ORDER_TOTAL의 디폴트 값은 0 


create table orders(
	order_id number(12),
	order_date date,
	order_mode varchar2(8),
	customer_id number(6),
	order_status number(2),
	order_total number(8,2) default 0,
	sale_rep_id number(6),
	promotion_id number(6),
constraint pd_order primary key(order_id),
constraint ck_mode check(order_mode in('direct', 'online'))

);

​
-- 테이블 수정 & 제약사항 수정 연습
-- CUSTOMER_ID 필드명을 CUSTOMER_NUMBER 로 수정
alter table orders rename column customer_id to customer_number;

-- Promotion_ID 값은 10000~99999 사이의 값만 저장 가능
alter table orders add constraint pro_check 
check( promotion_id between 10000 and 99999);

-- 테이블의 복사
CREATE TABLE orders_2 AS SELECT * FROM orders;
-- as select 구분은 select 구문 이후에 다시 학습 합니다

-- 테이블의 제거
DROP TABLE orders_2 purge; --purge 는 생략 가능
-- purge 가 없이 삭제된 테이블은 나중에 휴지통 과 같이 복구가 가능
-- purge 를 사용하면 완전 삭제

-- 휴지통에 있는 데이터 조회
SELECT * FROM RECYCLEBIN;
-- 삭제된 정보가 나오며 ORIGINAL_NAME과 OPERATION을 활용하여 복구가 가능합니다.


-- 휴지통 비우기
PURGE RECYCLEBIN


-- 테이블 복구하기
FLASHBACK TABLE orders_2 TO BEFORE DROP

​