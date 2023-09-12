package springmvc.lab.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springmvc.lab.entity.Job;
import springmvc.lab.repository.EmployeeDao;
import springmvc.lab.repository.JobDao;

@Controller
@RequestMapping("/lab/job")
public class JobController {
	// 下面要使用 employeeDAO 資料, 所以多 Autowired employeeDao
	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@GetMapping("/")
	public String index(@ModelAttribute Job job, Model model, HttpSession httpSession) {
		model.addAttribute("_method", "POST");
		model.addAttribute("jobs", jobDao.query(httpSession.getAttribute("offset"))); // 取得 session 裡面的 offset 的值
		model.addAttribute("count", jobDao.getCount());
		model.addAttribute("employees", employeeDao.query());
		return "lab/job";
	}
	
	@GetMapping("/page/{num}")
	public String page(@PathVariable("num") int num, HttpSession httpSession) {
		if(num >= 0) {
			// 這裡是唯一設定(setAttribute) offset 的地方
			httpSession.setAttribute("offset", (num-1) * JobDao.LIMIT); // offset 要 -1, 因為 offset 是從 0 開始
		} else {
			httpSession.removeAttribute("offset");
		}
		return "redirect:../";
	}
	
	@GetMapping("/{jid}")
	public String get(@PathVariable("jid") Integer jid, Model model, HttpSession httpSession) {
		model.addAttribute("_method", "PUT");
		model.addAttribute("job", jobDao.get(jid));
		model.addAttribute("jobs", jobDao.query(httpSession.getAttribute("offset")));
		model.addAttribute("count", jobDao.getCount());
		model.addAttribute("employees", employeeDao.query());
		return "lab/job";
	}
	
	@PostMapping("/")
	public String add(@Valid Job job, BindingResult result, Model model, HttpSession httpSession) {
		if(result.hasErrors()) {
			model.addAttribute("_method", "POST");
			model.addAttribute("job", job);
			model.addAttribute("jobs", jobDao.query(httpSession.getAttribute("offset")));
			model.addAttribute("count", jobDao.getCount());
			model.addAttribute("employees", employeeDao.query());
			return "lab/job";
		}
		jobDao.add(job);
		return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid Job job, BindingResult result, Model model, HttpSession httpSession) {
		if(result.hasErrors()) {
			model.addAttribute("_method", "PUT");
			model.addAttribute("job", job);
			model.addAttribute("jobs", jobDao.query(httpSession.getAttribute("offset")));
			model.addAttribute("count", jobDao.getCount());
			model.addAttribute("employees", employeeDao.query());
			return "lab/job";
		}
		jobDao.update(job);
		return "redirect:./";
	}
	
	
}
