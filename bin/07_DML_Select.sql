--오라클 명령어 : select 문(검색)**

-- 사용 양식(일반)
-- Select * from 테이블 명

--[1] 현재 계정 사용자가 관리(소유)하고 있는 테이블의 목록
select * from tab;

​
--[2] 특정 테이블의 구조 조회(필드 리스트/데이터 형식)
desc dept; -- 커멘드 창(sqlplus)에서 확인 요망
desc PERSON; -- 커멘드 창(sqlplus)에서 확인 요망

​
-- SELECT : select 와 from 사이에 조회하고자 하는 필드명들을 ,로 구분하여 지목
-- select booknum, subject, outprice from....
-- 모든 필드를 한번에 지목하려면 * 을 써줍니다 -- select * from...
-- from 뒤에는 대상 테이블 명을 써줍니다.
-- where 절을 붙여서 조건에 맞는 행만 골라내기도 합니다.
-- select ... from... where ...
-- 위와 같이 연산식을 써서 연산된 결과를 필드로 조회하고자 할땐 as 와 함께 만들어진 필드명을 지어주기도 합니다
-- select empno || '-' || ename as emp_info from emp
-- empno || '-' || ename : empno 값과 ename 값을 '-' 와 함께 이어 붙이기 하고 그렇게 만들어진
-- 필드의 이름을 emp_info 로 설정합니다
-- 필드명에 공백이 있거나 기술하기 어려운 필드명일때도 as로 별칭을 붙이기도 합니다.

select empno || '-' || ename as emp_info from emp


--[3] 특정 테이블의 DATA 표시
select * from IN_OUT; 

--[4] 모든 컬럼(필드명)이 아닌 , 필요한 필드만 조회
select subject, makeyear from booklist;

--[5] 각각의 필드명에 별칭을 부여해서 출력
select subject as 영화제목, makeyear as 출판년도 from booklist;
--(주의) 별칭에 띠어 쓰기 하지 않습니다. 띠어 쓰기가 들어간 별칭을 쓰려면 큰따옴표로 묶어서 사용합니다
select subject as "영화 제목" , makeyear as "출판 년도" from booklist;

--[6] 중복제거
SELECT distinct booknum FROM IN_OUT;
-- in_out 테이블에서 booknum 값만을 조회하되 중복된값은 한번만 표시하도록 조회합니다

--[7] 검색 조건의 추가 : 입고가격이 20000원 이상인 book 목록 
select * from booklist where inprice >=20000;

--[8] 이름이 박지성인 회원의 모든 회원정보 출력
select * from person where personname='박지성';

--[9] 1983년도 이후로 태어난 회원의 모든 회원정보
select * from person where birth >= '1981/01/01';

--[10] 사은포인트(BPOINT)가 250점 이상이고 1982년 이후로 태어난회원의 모든 회원정보
select * from person where bpoint>=250 and birth>='1982/01/01';

--[11] 제작년도가 2016년 이전이거나 입고가격(inprice)이 18000 이하인 book 정보
select * from booklist where makeyear<2016 or inprice<=18000;

--[12] 성명이 '이'로 시작하는 회원의 모든 정보
select * from person where personname like '이%';

--[13] 이름이 '용'으로 끝나는 회원의 정보
select * from person where personname like '%용';

--[14] 이름이 '이'가 포함되는 회원정보
select * from person where personname like '%이%';

--[15] 사은포인트가 NULL 이 아닌 회원의이름과 전화번호
select personname, phone from person where bpoint is not null;

--[16] 도서 제목에 두번째 글자가 '것' 인 도서 정보
select * from booklist where subject like '_것%';

​

-- 조건식(ANY, SOME, ALL , (IN) )
-- 'where 절'에서 사용하는 그룹내 해당 요소 찾기 함수들
--1. ANY
select empno, sal, deptno from emp where deptno = any(10,20,30,40);

-- 위와 아래의 조건식 결과 동일함
select empno, sal, deptno from emp
where deptno=10 or deptno=20 or deptno=30 or deptno=40;
-- ANY() 괄호안에 나열된 내용중 어느하나라도 해당하는 것이 있다면 검색 대상으로 함

--2. ALL
SELECT empno, sal FROM emp WHERE sal = ALL (2000, 3000, 4000);
-- 괄호안의 모든값이 동시 만족해야하는 조건이므로 해당하는 레코드가 없을때가 대부분입니다. 사용빈도수가 낮습니다

--3. --SOME 조건식 - ANY와 동일 - IN 과도 동일
SELECT empno, sal, deptno FROM emp WHERE deptno = some(10,20,40);

--4. -- 논리조건식 NOT
SELECT empno, sal FROM emp WHERE deptno NOT in(10,20,30,40);
-- in() 안에 있는것과 하나도 매칭되지 않은 값이 검색대상

--5. -- EXISTS 조건식
-- in 과 비슷하지만 후행 조건절로 값의 리스트가 아닌 서브 쿼리만 올수 있음
-- 또한 서브 쿼리내에 조인 조건이 있어야 정상작동 - JOIN 단원을 학습한 후 다시 자세히 공부합니다.
select deptno, dname from dept a
where exists (select * from emp b where a.deptno = b.deptno and b.sal > 3000);

​
-- 정렬(Sort) - Oder by 필드명 [desc]
-- select 명령의 결과를 특정 필드값의 오름차순 이나 내림 차순으로 정렬하라는 명령
-- asc :오름차순 정렬 , 쓰지 않으면 기본 오름차순 정렬로 실행됩니다
-- desc : 내림차순 정렬, 내림 차순 정렬을 위해서는 반드시 필드명 되에 써야하는 키워드 입니다

-- emp 테이블에서
-- sal이 1000 이상인 데이터를 ename 의 오름차순으로 정렬하여 조회
select * from emp where sal>=1000 order by ename; -- 오름차순 asc 는 생략

-- sal이 1000 이상인 데이터를 ename 의 내림차순으로 정렬하여 조회
select * from emp where sal>=1000 order by ename desc;

-- job으로로 내림차순 정렬
select * from emp order by job desc;
-- job 으로 내림차순 정렬후 같은 job_id 사이에서는 순서를 hiredate 의 내림차순으로 정렬
select * from emp order by job desc, hiredate desc;
-- 두가 이상의 정렬 기준이 필요하다면 위와 같이 (,)로 구분해서 두가지 기준을 지정해주며,
-- 위의 예제로 봤을때 job 으로 1차 내림 차순 정렬하고, 같은 job 값들 사이에 hiredate 로 내림차순정렬합니다


-- 그 외 활용하기 좋은 select 에 대한 예제
-- 부서번호가 10이 아닌 사원 (아래 두 문장은 같은 의미의 명령입니다)
select * from emp where not (DEPTNO=10);
select * from emp where DEPTNO<>10;

-- 급여가 1000달러 이상 , 3000달러 이하
select * from emp where SAL>=1000 and SAL<=3000;
select * from emp where SAL between 1000 and 3000;

-- 특정 필드값이 널인 레코드 또는 널이 아닌 레코드
select * from emp where comm is null; -- comm 필드가 null 인 레코드
select * from emp where comm is not null; -- comm 필드가 null 이 아닌 레코드

--사원의 연봉 출력
select deptno, ename, sal*12 as 연봉 from emp;

--커미션을 포함한 사원의 연봉 출력
select deptno, ename, sal*12+comm as 연봉 from emp;
-- 위 계산식의 경우 comm 값이 널인경우 계산식에 널이 포함되어 계산이 안되는 오류가 발생할 수 있습니다

-- 해결방법
select deptno, ename, comm, sal*12+nvl(comm,0) as 연봉 from emp; 
-- nvl 함수는 널값을 다른 값으로 바꿔주는 내장함수로서 다음 단원에서 다른 함수들과 함께 학습합니다
select * from emp;

