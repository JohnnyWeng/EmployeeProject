package springmvc.lab.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eid;

	@Size(min = 2, max = 50, message = "{employee.ename.size}")
	private String ename; // 資料庫用 ename

	@NotNull(message = "{employee.salary.empty}")
	@Range(min = 30000, max = 300000, message = "{employee.salary.range}") // 年薪只能在 30000 ~ 300000
	private Integer salary;

	// MM: 月份, mm: 分
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 返回實際日期型態 (傳給 view 表單的資料)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 接收日期型態 (接收 view 表單傳送過過來的日期資料)
	private Date createtime;

	// 加入 Job : 一個員工可以有多個 Job(一對多)
	@OneToMany(mappedBy = "Employee")
	private List<Job> jobs; // 放 Job 的 List

	private Integer orderNumber;
//	public Employee(String ename, int salary) { // 自己加的建構子
//		this.ename = ename;
//		this.salary = salary;
//	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	// add order number -> 小心如果多加任意變數, 都要改 toString, 否則在 contorller 或任何地方印出 Entity 都不會有那些新增的資料
	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", createtime=" + createtime
				+ ", jobs=" + jobs + ", orderNumber=" + orderNumber + "]";
	}
	
//	@Override
//	public String toString() {
//		return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", createtime=" + createtime
//				+ ", jobs=" + jobs + "]";
//	}

}
