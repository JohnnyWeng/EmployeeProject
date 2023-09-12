package springmvc.lab.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import springmvc.lab.entity.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
	private static final int LIMIT = 5; // for SQL
	@Autowired
	private JdbcTemplate jdbcTemplate; // 記得 EmployeeDaoImpl 要 Autowired JdbcTemplate
	
    private static final Logger logger = LogManager.getLogger(EmployeeDaoImpl.class); // logger 初始化
    
	// 不論是印出資料, 輸出在頁面上, 只要是撈出資料拿來用, 都是用 SELECT 語法
	// 查詢全部資料-不分頁
	@Override
	public List<Employee> query() { // 查詢全部資料-不分頁 -> 當你一進入 emp 就會來到這邊去搜 sql 資料
	// Native SQL

		String sql = "\n\t SELECT e.eid, e.ename, e.salary, e.createtime, \n"
				+ "	 j.jid as job_jid, j.jname as job_jname, j.eid as job_eid \n\t"
				+ " from employee e left join job j \n\t"
				+ " on e.eid = j.eid \n\t"
				+ " order by e.eid \n\t";
				
//		我們要在 Maven(Pom.xml) 去加入 Simple flat mapper 才能使用 JdbcTemplateMapperFactory
		ResultSetExtractor <List<Employee>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys("eid")
				.newResultSetExtractor(Employee.class);
		
		List<Employee> employees = jdbcTemplate.query(sql, resultSetExtractor); // resultSetExtractor: sfm support one to many by using resultSetExtractor
		logger.debug(" query sql = " + sql);
		return employees; // 要傳回去才能在頁面顯示資料庫內的東西
	}
	
	
	// 查詢全部資料-透過 httpSessionValue 值決定是否要分頁
	@Override
	public List<Employee> query(Object httpSessionValue) {		
		return null;
	}
	// 查詢資料-分頁查詢 (offset 從第幾筆開始查)
	@Override
	public List<Employee> queryPage(int offset) { // 把上面 query() copy 過來
		String sql = " SELECT e.eid, e.ename, e.salary, e.createtime, "
				+ "	j.jid as job_jid, j.jname as job_jname, j.eid as job_eid "
				+ " from employee e left join job j "
				+ " on e.eid = j.eid"
				+ " order by e.eid "; // 用 id 來排序, 才再分頁
		
		// 分頁查詢
		// SQL Mapping 上面的 sql
		if(offset >= 0) { // sql 分頁查詢語法 -> limit 限制每一頁有幾個
			sql += String.format(" limit %d offset %d ", LIMIT, offset); // LIMIT 是從上面來的
		}	
			
//		我們要在 Maven(Pom.xml) 去加入 Simple flat mapper 才能使用 JdbcTemplateMapperFactory
		ResultSetExtractor <List<Employee>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys("eid")
				.newResultSetExtractor(Employee.class);
		
		List<Employee> employees = jdbcTemplate.query(sql, resultSetExtractor);
		return employees;
	}

	@Override
	public Employee get(Integer eid) { // 取得
		String sql ="select eid, ename, salary, createtime from employee where eid=?";
		// 這裡就是用一般的 BeanPropertyRowMapper 而不是 SimpleFlatMapper
		return jdbcTemplate.queryForObject(sql, new Object[] {eid}, new BeanPropertyRowMapper<Employee>(Employee.class));
	}

	@Override
	public int add(Employee employee) { // 新增
		String sql ="Insert into employee(ename, salary) values(?, ?)";
		return jdbcTemplate.update(sql, employee.getEname(), employee.getSalary());
	}
	
	// 把更新後的物件傳進來, 然後裡用其 name, salary 去 update 資料
	@Override
	public int update(Employee employee) { // 更新
		String sql ="update employee set ename=?, salary=? where eid=?";
		return jdbcTemplate.update(sql, employee.getEname(), employee.getSalary(), employee.getEid());
	}
	
	
	@Override
	public int delete(Integer eid) { // 根據 eid 來刪除
		String sql ="delete from employee where eid=?";
		return jdbcTemplate.update(sql, eid);
	}
	
}




