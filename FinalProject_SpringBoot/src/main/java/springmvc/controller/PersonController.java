package springmvc.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springmvc.entity.Person;

// 下面有兩種方法(這兩種寫法是一樣的意思)
@Controller
@RequestMapping("/person")
public class PersonController {			// 寫法 1: 通常用這種寫法, 因為已經用 ModelAttribute 了
	// 記得這種 List 多型寫法(只能放 Person 物件) 多型 CopyOnWriteArrayList
	// people 當作資料庫
	private List<Person> people = new CopyOnWriteArrayList<>(); // ArrayList 的加強版, 執行續安全
		
	// @ModelAttribute Person person 會自動丟入 person 物件給 view: ModelAttribute 先建立好 person, 然後再放下去(就不用像下面手動加 addAttribute())
	// 自動執行 model.addAttribute("people", people); 不像寫法 2 要手動加入 Person
	@GetMapping("/") // 首頁就是 http://localhost:8080/springmvc/mvc/person/ 最後面有 /
	public String index(@ModelAttribute Person person, Model model) { // ModelAttribute 是 Person 的, 那我們也要是其他資料給 Model
		person.setAge(18);
		model.addAttribute("people", people); // people 是上面的資料庫, 把 people 資料庫放進 model
		// URL: ${pageContext.request.contextPath}/mvc/person/ -> 頁面: person/index.jsp
		return "person/index"; // 傳回 person 下的 index.jsp
	}
	
	
	/*
	// 效果等同於上方: 但是這裡因為 person, model... 都不寫在 () 裡面, 所以要 new Person()
	@GetMapping("/")
	public String index2(Model model) { // 寫法 2 (要 new person, 假如要給預設值就用第二種寫法)
		model.addAttribute("people", new Person());
		person.setAge(18);
		model.addAttribute("person", person);
		model.addAttribute("people", people);
		return "person/index";
	}*/
	
	//---------------------------------------------------------------------------------------------------------------------------
	
	// @Valid Person person -> person 物件需經過驗證,　拿到 person 要經過 validate 要驗證 (JSR 303)
	// BindingResult result -> 紀錄上述的驗證結果(去 Person.java 用 Hibernate 驗證, 也就是說就不用 if-else 去驗證)
	// 記住: 記住新增(add)資料要用 POST。 跟上面不一樣, 首頁用 GetMapping
	@PostMapping("/") // 網頁變成 ...8080/Springmvc/mvc/person/
	public String add(Model model, @Valid Person person, BindingResult result) { 
		// 判斷是否有錯誤發生 ?
		if(result.hasErrors()) {
		// 會自動將錯誤訊息丟到指定 view 物件 ("person/index") 中, 也就是回首頁
			// 把 people 放進 model 叫做 "people", 最後給 jsp 去做顯示
			model.addAttribute("people", people);
			return "person/index";
	}
	// 把 person 物件(已經驗證完的 person 物件)放進 people (List 資料庫)
	people.add(person); // 如果沒有錯誤, 就會重導到首頁
	return "redirect:./";
	}
}

// Thread safety (review): Java_OCP_JP 809, Java_04, p.52
// we need to write implementations in a thread-safe way. This means that different threads can access the same resources without exposing erroneous behavior or producing unpredictable results. 
// This programming methodology is known as “thread-safety”.

// thread-safety or thread-safe code in Java refers to code that can safely be utilized or shared in concurrent or multi-threading environment and they will behave as expected.
// Thread-safety is one of the risks introduced by using threads in J ava and I have seen java programmers and developers struggling to write thread-safe code or just understanding what is thread-safe code and what is not?