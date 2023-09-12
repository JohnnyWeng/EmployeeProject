package springmvc.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springmvc.entity.User;
import springmvc.service.UserService;

/*
 * API Spec:
 * http://localhost:8080/Springmvc/mvc/user/input
 * http://localhost:8080/Springmvc/mvc/user/add?name=mary&age=19
 * http://localhost:8080/Springmvc/mvc/user/update/mary?age=20
 * http://localhost:8080/Springmvc/mvc/user/delete/mary
 * http://localhost:8080/Springmvc/mvc/user/read
 * http://localhost:8080/Springmvc/mvc/user/get/mary
*/

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired // 記得之前在 Spring_Case2_MySQL 寫的, 這是綁定 Userservice, 就不用建構子創造物件去做 (UserService userService = new UserService();)
	private UserService userService; 
	// 注意下面回傳的都是 String 
	@RequestMapping("/input")
	public String input(Model model) { // Spring MVC Model Interface: 看下面解說
		// 以前用 requestDispatcher 現在用 model, 都是把資料傳給 jsp
		model.addAttribute("action", "add"); // add action to the model。Key-Value Pairs ->  key: action, value:add
		model.addAttribute("readonly", "add"); // Read Only -> 新增的時候沒事, 但是 update 的時候就不能
		// 最後把這個 model 返回一個jsp，在 user_form.jsp 頁面上就能得到這個 student 物件了
		return "user/user_form"; // 返回 user_form.jsp 字串串接 (導入到 user_form.jsp) -> 所以要刪除 @ResponseBody
	}
	
	@RequestMapping("/add")
	public String add(@Valid User user, BindingResult result) { // validate User
		if(result.hasErrors()) {
			return String.format("Add User Fail: %s", result); // result: 顯示錯誤
		}
		userService.create(user); // 呼叫 create 方法, 把已經確認過的 user 放進資料庫(UserService 做的)
		// 如果你把 toString() 寫在 UserService.java 的 read() 方法, 應該這裡就不用寫了
		// return userService.read().toString(); // toString: Object 方法, Returns a string representation of the object
		System.out.println();
		// add 加完就會重新導到 read (要把上面的 @ResponseBody, 因為這裡是字串串接)
		//.../mvc/user/read -> .../mvc/user/input (read 要先回到上一層(./), 才能再導到 input)
		return "redirect:./read"; // ./read 先回 user 再去 read
	}
	@RequestMapping("/read")
	public String read(org.springframework.ui.Model model) { // ModelAndView 的 model, 小心不要 import 錯了(org.springframework.ui),否則會沒有 addAllAttributes 方法
		List<User> users = userService.read(); // 回傳整個 users 物件(資料庫)
		System.out.println("/read -> " + users); // 寫一個 log (在 console 會顯示), 也可以是 console.log()
		// 將資料傳給 jsp 去渲染
		model.addAttribute("users", users); // key: users, value: users 物件
		// 因為沒有加上 @ResponseBody 所以要拼裝
		return "user/user_list"; // "/WEB-INF/jsp/user/user_list.jsp" 自動拼裝
	}
	
	@RequestMapping("/get/{name}") // 取得名字, {name} 就直接寫要輸入的名字就好
	public String get(@PathVariable("name") String name, Model model) { // 這裡 name 就是參數
		Optional<User> optUser = userService.getByName(name); // 因為傳回的是 Optional<User> optUser。 Controller 和 Service 名稱可以相同。
		System.out.println("get -> " + name + " " + optUser.isPresent()); // Log
		// return optUser.isPresent()?optUser.get().toString():""; // 如果你把 toString() 寫在 UserService.java 的 getByName() 方法, 應該這裡就不用寫了
		if(optUser.isPresent()) {
			model.addAttribute("user", optUser.get());
		}
		// 因為 update 前面是做字串拼接, 所以不加 "/"
		model.addAttribute("action", "update/" + name);
		model.addAttribute("readonly", "readonly");
		return "user/user_form";
	}
	
	@RequestMapping("/update/{name}")
	// 其實 name 應改可以不用寫 -> If the name of the method parameter matches the name of the path variable exactly, then this can be simplified by using @PathVariable with no value
	// 但是 age 不能省略, 因為 age 與 newAge 不一樣
	public String update(@PathVariable("name") String name, @RequestParam("age") Integer newAge) {
		boolean isSuccess = userService.updateAgeByName(name, newAge); // 一樣去 Service 呼叫 update 功能, 把 name 和 age 傳過去方法
		System.out.println("/update --> " + name + " " + newAge + isSuccess); // log
		return "redirect:../read"; // 與 delete 一樣, 改完後回到 read 畫面
	}
	
	@RequestMapping("/delete/{name}") // 小心 {name} 是不能有空格的
	public String delete(@PathVariable("name") String name) {
		boolean isSuccess = userService.deleteByName(name); // 利用 Service 層傳入 name, 刪除對應的物件
		System.out.println("/delete -> " + name + " " + isSuccess); // log
		// ../read -> 回上兩層(/delete/{name}) 再到 read
		return "redirect:../read"; // 刪除資料後, 還是回到 read 畫面, 記住寫法, redirect: 就是重新導向 url
	}
}

// 這裡只是練習, Service 只是假的 database
// 專案流程: Controller -> Database(Service) -> Entity
// 寫程式流程(順序): Entity -> Database(Service) -> Controller [也就是反者寫]


// Spring MVC Model Interface:
// In Spring MVC, the model works a container that contains the data of the application. Here, a data can be in any form such as objects, strings, information from the database, etc.
// It is required to place the Model interface in the controller part of the application. The object of HttpServletRequest reads the information provided by the user and pass it to the Model interface. 
// Now, a view page easily accesses the data from the model part.



















