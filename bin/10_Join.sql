select * from emp;
select * from dept;

--[1] 이름 scott이 근무하는 부서명, 부서의 지역명 출력(서브 쿼리 사용)
-- select 두번 사용
select deptno from emp where ename = 'SCOTT'; --20
select dname, loc from dept where deptno = 20;  

-- select dname, loc from dept where deptno = (  ); 
-- 괄호안에 select deptno from emp where ename = 'SCOTT'; 명령을 삽입
select dname, loc from dept 
where deptno = (select deptno from emp where ename = 'SCOTT');

-- 위 둘의 공통 단점 : 결과의 SCOTT 이라는 이름이 나오지 않습니다.
-- 서브쿼리의 단점 : 서브쿼리의 결과가 2건이상이면 뜻하지 않는 오류를 초래합니다.
-- 서브쿼리의 단점을 해결을 위해  = 대신 in을 씁니다.
select dname, loc from dept 
where deptno in(select deptno from emp where ename = 'SCOTT');
-- where deptno in(10,20 ..)와 같은 형식이므로 in 안에 데이터만큼 결과가 출력됩니다.


-- [2] join : 두개 이상의 테이블에 나뉘어져 있는 데이터를 한번에 sql문으로 원하는 겨로가를 얻음
-- 위에서 언급한 단점들이 한번에 해결되는 방법


-- cross join : 두개 이상의 테이블이 조인될때 where절에 의해 공통되는 칼럼에 의한 결합이 발생하지 
-- 않는 경우이며, 가장 최악의 결과를 얻는 조인방식 
-- A테이블의 1레코드와 B테이블의 모든 레코드 조합
-- A테이블의 2레코드와 B테이블의 모든 레코드 조합
-- A테이블의 3레코드와 B테이블의 모든 레코드 조합
-- ...
-- A테이블의 레코드가 8개, B테이블의 레코드가 7개라면 총 크로스조인의 결과 레코드 수는 8X7 = 56
-- A테이블의 레코드가 8개, B테이블의 레코드가 3개라면 총 크로스조인의 결과 필드 수는 8+3 = 11
select * from emp, dept;

-- update emp set deptno = 20 where ename like 'S%'; -- 따옴표 안에 있는 것은 대소문자 구별됨


-- equi join : 조인 대상이 되는 두테이블에서 공통적으로 존재하는 칼럼의 값이 일치하는
-- 행을 연결하여 결과를 생성
-- 시작은 cross join -> 값은 값을 갖는 필드를 조건으로 걸어 필터링합니다.
select * from emp, dept -- 시작

select * from emp, dept
where emp.deptno = dept.deptno; -- 두테이블의 부서번호가 같은 레코드만 필터링(14개만 골라냄)

select * from emp, dept
where emp.deptno = dept.deptno and ename ='SCOTT'; -- 이름SCOTT만 필터링 
-- and ename ='SCOTT' 행을 필터링 함

select ename, dname, loc from emp, dept -- select ename, dname, loc 열을 필터링
where emp.deptno = dept.deptno and ename ='SCOTT'; -- 필드 일부를 필터링

-- 출력내용에 부서번호 추가
select ename, deptno, dname, loc from emp, dept 
where emp.deptno = dept.deptno and ename ='SCOTT'; --에러
select ename, emp.deptno, dname, loc from emp, dept 
where emp.deptno = dept.deptno and ename ='SCOTT'; --정상실행

-- 필드명 앞에 테이블 명을 기술하여 필드의 소속을 명확히 밝힐 수 있다
select emp.ename, dept.dname, dept.loc, emp.deptno from emp, dept
where emp.deptno = dept.deptno and emp.ename = 'SCOTT'; 

select a.ename, b.dname, b.loc, a.deptno from emp a, dept b
where a.deptno = b.deptno and a.ename = 'SCOTT';
-- 테이블 명에 별칭을 부여한 후 칼럼앞에 소속테이블을 별칭을 써줄 때는 
-- 모두 반드시 별칭을 기술해야합니다. 

--equi 조인 연습
-- in_out, booklist 테이블을 이용하여 대여일자, 도서코드, 도서제목, 출판년도 equi조인으로
-- 출력하세요 필드명은 위에있는 이름으로 별칭을 표시하고, 테이블이름도, a, b 로 지정하여 
-- 필드명에 적용하세요

select a.out_date as 대여일자, a.booknum as 도서코드,
		  b.subject as 도서제목, b.makeyear as 출판년도
from in_out a, booklist  b
where a.booknum = b.booknum; -- **테이블에 기록되어있어야 맞음 앞파일 기록확인해야함


-- in_out, person 테이블을 이용하여 대여일자, 회원이름, 전화번호, 사은포인트(bpint)를 equi조인으로
-- 출력하세요 필드명은 위에있는 이름으로 별칭을 표시하고, 테이블이름도, a, b 로 지정하여 
-- 필드명에 적용하세요
select a.out_date as 대여일자, a.personname as 회원이름, --**확인필요!
		  b.phone as 전화번호, b.bpoint as 사은포인트
from in_out a, person b
where a.personnum = b.personnum;

-- 세개의 테이블(in_out, person, booklist) 을 equi join으로 조합
-- 표시할 필드명칭 : 대여일자, 도서제목, 회원이름, 대여가격, 할인금액, 매출액
-- 테이블 별칭 : in_out: a, person : b, booklist : c

select * from booklist;
select * from person;
select * from in_out;

select a.out_date as 대여일자, c.subject as  도서제목, b.personname as 회원이름,
		  c.outprice as 대여가격, a.discount as 할인금액, c.outprice-a.discount as 매출액
from in_out a, person b, booklist c
where a.booknum = c.booknum and a.personnum = b.personnum;
-- select * from booklist, person, in_out;

-- non-equi join
-- 동일 칼럼이 없이 다른 조건을 사용하여 조인
-- 조인조건에 특정 범위내에 있는지를 조사하기 위해 조건절에 조인 조건을 = 연산자 이외의 비교
-- 연산자 이용
select * from emp;
select * from salgrade;
-- 급여가 losal 보다 크고, hisal보다 작은 조건에 맞게 레코드를 찾아서 grade 를 출력합니다.
select a.ename, a.sal, b.grade from emp a, salgrade b
where a.sal >= b.losal and a.sal <= b.hisal;

select a.ename, a.sal, b.grade from emp a, salgrade b
where a.sal between b.losal and b.hisal;

-- outer join
-- 조인 조건에 만족하지 못해서 해당 결과를 출력시에 누락이 되는 문제점이 발생할 때
-- 해당 레코드를 출력하는 조인
select a.booknum, a.subject, b.out_date from booklist a, in_out b
where a.booknum = b.booknum(+);

-- 연습문제
-- outer 조인으로 emp테이블의 인원에 대한 부서명을 출력하되 부서명이 없는 필드는 
-- NULL 값으로 표시하세요
alter table emp drop constraint fk_deptno;
update emp set deptno = 50 where job = 'CLERK';
update emp set deptno = 60 where job = 'ALALYST';

select a.ename, b.dname from emp a, dept b
where a.deptno = b.deptno(+);

select * from emp a, dept b
where a.deptno = b.deptno(+);

--[3] ANSI join
--		(1) Ansi Cross join
select * from emp cross join dept --일반 크로스조인과 같은 효과
--		(2) Ansi inner join --일반 equi조인과 같은 효과
-- 기존 equi 조인의 표현방식
select ename, dname from emp a, dept b where a.deptno = b.deptno
-- Ansi 이너 조인의 표현방식
select ename, dname from emp inner join dept on emp.deptno = dept.deptno;
select ename, dname from emp inner join dept on emp.deptno = dept.deptno
where ename = 'SCOTT';
-- 두테이블의 조인 기준이 되는 필드명이 똑같을 때만 사용가능
-- 		(3) Ansi Outer Join
--	기존 아우터 조인의 표현방식
select * from emp, dept where emp.deptno = dept.deptno(+);
select * from emp, dept where emp.deptno(+) = dept.deptno;
-- Ansi Outer Join
select * from emp left outer join dept on emp.deptno = dept.deptno;
select * from emp right outer join dept on emp.deptno = dept.deptno;
-- 기분이 되는 필드명 중 A테이블의 필드에는 있으나 B테이블 필드에는 해당 값이 없는 경우에 대한
-- 표현 여부 결정

