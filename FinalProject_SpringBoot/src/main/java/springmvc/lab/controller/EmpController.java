package springmvc.lab.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springmvc.lab.entity.Employee;
import springmvc.lab.repository.EmployeeDao;

@Controller
@RequestMapping("/lab/emp")
public class EmpController {
// 下面這四個 "HTTP 方法" 是針對 Server 上面指定位置的操作
//	POST = 新增 (將資訊包含在 Request 中送給 Server 中指定位置之程式處理) -> add
//	GET = 讀取 (取得 Server 中指定位置的檔案) -> Search
//	PUT = 更新 (將資訊儲存至 Server 指定位置) -> update
//	DELETE = 刪除 (刪除 Server 中指定位置的資源)

	private static final Logger logger = LogManager.getLogger(EmpController.class); // logger 初始化

	@Autowired // == CDI, @Inject
	private EmployeeDao employeeDao; // Autowired (引入) employeeDao 這個 Dao
	
	@GetMapping("/") // 利用 URL: /lab/emp/ 導到這個方法
	public String index(@ModelAttribute Employee employee, Model model) {
		logger.debug("[Searching Start]");

		model.addAttribute("_method", "POST");
		// 把取出的值塞進去 model, 物件名叫做 "employees"
//		model.addAttribute("employees", employeeDao.query()); // 如果只是撈值就直接丟到前端即可
		List<Employee> query = employeeDao.query(); // employeeDao.query() -> 查詢全部資料-透過 httpSessionValue 值決定是否要分頁
		for (int j = 0; j < query.size(); j++) { // for calculating 編號(orderNumber)
			query.get(j).setOrderNumber(j + 1); // set ordernumber: 1,2,3,4,5...
		}

//		}
		model.addAttribute("employees", query);
		logger.debug("query = " + query);
//		logger.debug("model = " + model);
		return "lab/employee"; // 導頁到 employee.jsp -> URL: http://localhost:8080/springmvc/mvc/lab/employee.jsp (.jsp 在 springmvc-servlet 定義)
	}

	@PostMapping("/") // 新增
	public String add(@Valid Employee employee, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method", "POST");
			model.addAttribute("employees", employeeDao.query()); // 主要是先確認能夠把資料撈出, 看看有沒有錯
			model.addAttribute("employee", employee);
			return "lab/employee";
		}
		employeeDao.add(employee);
		return "redirect:./";
	}
    // 當使用者選擇一個姓名去修改,就會跑來這邊 
	@GetMapping("/{eid}") // 查詢 -> 單筆和多筆
	public String get(@PathVariable("eid") Integer eid, Model model) {
		model.addAttribute("_method", "PUT");
		
		List<Employee> employees = employeeDao.query(); 
		Employee singleEmployee = employeeDao.get(eid); // 從 Database 撈資料
		if (employees != null && employees.size()>0 && singleEmployee != null) { // 如果撈出的物件 aid 和 User 選擇的值 "相等"
			for (int j = 0; j < employees.size(); j++) {
				employees.get(j).setOrderNumber(j + 1); // 塞進物件, 而不是真的進入 db, 先把 ordernumbe 塞進employees 的 employee
				logger.info("!@$!$!$!$" + employees.get(j).getOrderNumber());
				if (employees.get(j).getEid().equals(singleEmployee.getEid())) {
					singleEmployee.setOrderNumber(j + 1); // 再把 employee 塞進 singleEmployee
				}
			}
		}
		model.addAttribute("employees", employees); // 多筆
//		model.addAttribute("employee", employeeDao.get(eid)); // 單筆 -> 用來鎖定單一筆可以做修改
		model.addAttribute("employee", singleEmployee);
		return "lab/employee";
	}

	// 用 "/" 或 "/update" 都可以, 自己去 mapping
	@PutMapping("/") // 修改
	public String update(@Valid Employee employee, BindingResult result, Model model) {
		// 如果有錯誤的話
		if (result.hasErrors()) {
			model.addAttribute("_method", "PUT"); // 修改出現錯誤還是要傳 PUT 回去 (update 也要看資訊是否符合)
			model.addAttribute("employees", employeeDao.query());
			model.addAttribute("employee", employee);
			return "lab/employee";
		}
		logger.debug("Update erors = " + result.hasErrors());
		logger.info("update Employee!$!$ = " + employee);
		employeeDao.update(employee); // 成功就 udpate, 反之就重導
		return "redirect:./";
	}

	@DeleteMapping("/")
	public String delete(Employee employee) {

//		logger.debug("employee.getEid()!@!=" + employee.getEid());

		employeeDao.delete(employee.getEid());
		return "redirect:./";
	}

	public static void main(String[] args){
		
		
	}
	
}

//	@DeleteMapping("/{eid}") // 刪除 -> 利用 employee 物件刪除
//	public String delete(@Valid Employee employee, Model model) {
//		model.addAttribute("_method", "DELETE");
//		employeeDao.delete(employee);
//		return "redirect:./";
//	}

