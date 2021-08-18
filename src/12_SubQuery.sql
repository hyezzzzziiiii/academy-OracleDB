-- 서브퀴리
-- 	Sub Query : 하나의 select 문장의 절안에 포함된 또하나의 select 쿼리문

update emp set deptno = 30 where ename = 'SCOTT';

-- SCOTT 의 근무하는 곳의 부서명과 지역 출력
-- 단일 행 서브뭐리 : 서브쿼리의 결과가 하나
select deptno from emp where ename ='SCOTT'; -- 결과는 30
-- 위 select 명령읙 결과를 다른 select명령(조건)에 사용
select dname, loc from dept
where deptno in (select deptno from emp where ename='SCOTT');

-- [연습문제] SCOTT과 동일직급(job)을 가진 사원을 출력하는 SQL문
select empno, ename, job from emp
where job = (select job from emp where ename = 'SCOTT');

-- [연습문제] SCOTT과 급여와 동일하거나 더 많이 받는 사원이름과 급여출력
select ename, sal from emp
where sal >= (select sal from emp where ename = 'SCOTT');


--[서브쿼리 & 그룹함수]
-- 전체 사원 평균 평균급여보다 더 많은 급여를 받는 사원의 이름, 급여, job
select ename, sal, job from emp where sal >=(select avg(sal) from emp);
-- 다중행 서브쿼리 : 서브쿼리의 결과가 둘이상(=, >= 말고 IN( ) )
-- 급여를 2000이상 받는 사원이 소속된 부서와 소속된 부서에서 근무하는 사원들의 이름, 부서번호, job
select deptno from emp where sal >= 2000; --급여 2000이상 사원의 부서번호
select distinct deptno from emp where sal >=2000; -- 10,20,30,60
--급여를 2000이상 사원의 부서번호 ('중복제거');
-- distinct 필드명: 필드값의 중복된 값이 여러개라면 한번만 출력
select ename, sal , job, deptno from emp
where deptno IN(select distinct deptno from emp where sal > = 2000);
-- where deptno IN(10,20,30,60) :부서번호가 10이거나 20이거나 30이거나 60인 조건


-- [연습문제] ** 여러복습 필요
-- 30 번 부서 소속 사원들 중에서 급여를 가장 많이 받는 사원 보다 ... 급여가 더 많은 사원의 이름과 job, 급여
select ename, job, sal from EMP 
where sal > (select max(sal)from emp where deptno = 30); --답안1

select ename, job, sal from EMP
where sal > all(select sal from emp where deptno = 30); -- 답안2
-- : 부서번호가 30인사원의 급여들 ... 그 모든 급여들 보다 큰 급여를 찾는 조건 - all의 유일한 사용예

-- 부서번호가 30번인 사람들의 급여중에서 가장 낮은 급여보다 ... 높은 급여를 받은 사원의 이름과 job, 급여
select ename, job, sal from EMP 
where sal > (select min(sal)from emp where deptno = 30); --답안1

select ename, sal from EMP 
where sal > any(select sal from emp where deptno = 30); -- 답안2

-- 영업사원 (job = 'SALESMAN' )들의 최소급여보다 많이 받는 사원들의 이름과 급여와 직급, 급여를
-- 출력하되 영업사원들은 출력하지 않습니다.
select ename, job, sal from emp
where sal > (select min(sal) from emp where job = 'SALESMAN')
			and job <> 'SALESMAN';
			
select ename, job, sal from emp
where sal > any(select sal from emp where job = 'SALESMAN')
			and job <> 'SALESMAN';


