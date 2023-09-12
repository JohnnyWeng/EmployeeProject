package springmvc.lab.repository;

import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import springmvc.lab.entity.Job;

@Repository
public class JobDaoImpl implements JobDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Job get(Integer jid) { // 取得單筆資料
		String sql = "select jid, jname, eid from job where jid=?";
		return jdbcTemplate.queryForObject(sql, new Object[] {jid}, new BeanPropertyRowMapper<Job>(Job.class));
	}

	@Override
	public int add(Job job) {
		String sql = "insert into job (jname, eid) values(?, ?)";
		return jdbcTemplate.update(sql, job.getJname(), job.getEid());
	}

	@Override
	public int update(Job job) {
		String sql = "update job set jname=?, eid=? where jid=?";
		return jdbcTemplate.update(sql, job.getJname(), job.getEid(), job.getJid());
	}
	
	// 查詢所有筆數 -> 分頁要用來計算的
	@Override
	public int getCount() {
		String sql = "select count(*) from job";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	// 不分頁全部查詢(-1)
	@Override
	public List<Job> query() {
		return queryPage(-1);
	}
	
	// 判斷 httpSession 值決定是否要分頁 -> 假設在第二頁修改，修改完還要跳回第二頁，我們要有 session ~
	// ~ 技術去判斷在哪一頁, 總不能網頁有 100 頁, 你在第 78 頁做修改, 然後還從第一頁開始修改
	@Override
	public List<Job> query(Object httpSessionValue) {
		if(httpSessionValue == null) { // 第一次進來沒有 Session
			return query();
		}
		return queryPage(Integer.parseInt(httpSessionValue + ""));
	}
	
	// 分頁查詢
	@Override
	public List<Job> queryPage(int offset) {
		ResultSetExtractor<List<Job>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys("jid")  // job 表的主鍵
				.newResultSetExtractor(Job.class);
		
		String sql = "select "
					+ "	j.jid, j.jname, j.eid, "
					+ "	e.eid as employee_eid, e.ename as employee_ename, e.salary as employee_salary, "
					+ "	e.createtime as employee_createtime "
					+ "from "
					+ "	job j, employee e "
					+ "where j.eid = e.eid "
					+ "order by j.jid ";
		// 分頁查詢, 
		if(offset >= 0) {
			sql += String.format(" limit %d offset %d ", LIMIT, offset); // LIMIT 定義在 JobDao
		}
		List<Job> jobs = jdbcTemplate.query(sql, resultSetExtractor);
		return jobs;
	}
}