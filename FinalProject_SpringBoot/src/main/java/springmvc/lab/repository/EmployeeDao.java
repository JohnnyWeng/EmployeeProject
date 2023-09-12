package springmvc.lab.repository;

import java.util.List;

import springmvc.lab.entity.Employee;

public interface EmployeeDao {
	// 查詢全部資料-不分頁 
	// 把整個 Emloyee 物件陣列傳回給 controller 放進 model 給 jsp 組做顯示
	public List<Employee> query();
	
	// 查詢全部資料-透過 httpSessionValue 值決定是否要分頁
	// 把整個 Emloyee  物件陣列傳回給 controller 放進 model 給 jsp 組做顯示
	public List<Employee> query(Object httpSessionValue);
		
	// 查詢資料-分頁查詢 (offset 從第幾筆開始查) -> 分頁是因為使用者假如要查詢 10000 筆資料, 那就要分頁來看
	// SQL 本身就有分頁的指令
	public List<Employee> queryPage(int offset);
	
	// 取得單筆資料
	public Employee get(Integer eid);
	
	// 新增
	public int add(Employee employee);
	
	// 修改
	public int update(Employee employee);
	
	// 刪除
	public int delete(Integer eid); // 根據 eid 來刪除資料, 記得使用 Integer
}






