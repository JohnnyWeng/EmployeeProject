-- Final Version: 8/6/2022
--執行順序, 記得每次都要 refresh 一下, 不然可能會出現 foreign 錯誤

-- 1. 建立 Employee 資料表
create table IF NOT EXISTS employee (
    eid integer not null auto_increment, -- 主鍵，員工 id (自行產生序號: 1, 2, 3, ...)
    ename text, -- 員工姓名
    salary integer, -- 員工薪資
    createtime datetime default current_timestamp, -- 建檔時間
    ordernumber Integer,
    primary key(eid)
);

-- 3. 建立 Employee 範例資料
insert into employee(ename, salary) values('John', 40000);
insert into employee(ename, salary) values('Mary', 50000);
insert into employee(ename, salary) values('Bobo', 60000);
insert into employee(ename, salary) values('Mark', 70000);

 2. 建立 Job 資料表
create table IF NOT EXISTS job
(
    jid integer not null auto_increment, -- 主鍵，工作 id
    jname text, -- 工作名稱
    eid integer not null, -- 員工 id
    primary key(jid)
    foreign key(eid) references employee(eid),  -- 外鍵關聯
    ON DELETE CASCADE -- The CASCADE option with ON DELETE allows deleting rows from the child table when the corresponding rows are deleted from the parent table.
);

-- 建立 Job 範例資料
insert into job(jname, eid) values('Job A', 1);
insert into job(jname, eid) values('Job B', 1);
insert into job(jname, eid) values('Job C', 2);
insert into job(jname, eid) values('Job D', 2);
insert into job(jname, eid) values('Job E', 4);
insert into job(jname, eid) values('Job F', 4);
insert into job(jname, eid) values('Job G', 4);

--------------------------------------------------------------------------------------------------------------------------

-- sql 查詢: 交集查詢
SELECT j.jid, jname, j.eid,
	   e.eid as employee_eid, e.ename as employee_ename, e.salary as employee_salary, e.createtime as employee_createtime
FROM job2 j, employee e
WHERE j.eid = e.eid

-- sql 查詢 2: 向左合併查詢
SELECT e.eid, e.ename, e.salary, e.createtime,
		j.jid as job_jid, j.jname as job_name, j.eid as job_eid
FROM employee e left join job2 j
on e.eid = j.eid;

-- select table
select * from employee e;
select * from job;

-- reset the id if necessary. You need to drop the job table first due to constraint. 
TRUNCATE TABLE employee; 

-- Delete table
DROP TABLE job;
DROP TABLE employee;

