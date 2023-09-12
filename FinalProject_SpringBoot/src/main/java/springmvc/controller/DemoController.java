package springmvc.controller;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult; // BindingResult 是 Springframework 裡面的
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvc.entity.User;


// (因為舊版的寫法是 implement Controller, 所以要 override, 要寫一大堆東西(ModelAndView handleR...), 新版的則不用~
// 母路徑: http://localhost:8080/Springmvc/mvc
@Controller // 不用像傳統方式要繼承 Controller
// 因為我們有加上 RequestMapping 定義整個 class, 所以所以下面的都要加上 /demo
@RequestMapping("/demo")  // http://localhost:8080/Springmvc/mvc/"demo"/hello -> 最後的 hello 又會被串接 jsp
public class DemoController {
	// 子路徑: /demo/hello
	@RequestMapping("/hello") // http://localhost:8080/Springmvc/mvc/demo/"hello"
	public String hello() { // springmvc-servlet.xml 會自動幫你加上 prefix 和 suffix
		return "hello"; // 呼叫(訪問) hello.jsp, 也就是會指向 /WEB-INF/jsp/hello.jsp 頁面
	}
	
	// 子路徑: /demo/helloString
	@RequestMapping("/helloString")
	// 因為有 @ResponseBody 的配置, 就不會幫你去拼裝 prefix 和 suffix(後贅詞) 去指向 hello.jsp 頁面 -> 在頁面上就是回傳 hello 
	// 也就是下面 hello 當成 body 回傳
	// 如果沒有加上 @ResponseBody 就會把下面字串當作 jsp 檔名, 幫你加上 prefix 和 suffix 
	@ResponseBody 
	public String helloString() {
		return "hello"; // 只用 hello, 不拼裝 jsp(也就是說不會去跑 .jsp)
	}
	
	// 傳入 name 與 age 參數
	// 子路徑: /demo/sayHi?name=John&age=18
	@RequestMapping("/sayhi") // 這裡路徑的 sayhi 和下面的 sayHi 名稱可以不同
	@ResponseBody 
	public String sayHi(@RequestParam(value = "name") String name, 
						@RequestParam("age") Integer age) {
		return "Hi " + name + ", " + age;
	}
	
	// 傳入 name(必要參數) 與 age(非必要參數) 參數
	// 子路徑: /demo/sayhello?name=John&age=18
	// 子路徑: /demo/sayhello?name=Mary  <- 這裡因為有 default value 所以可以不用設定 age
	// default value 預設是 "true", 如果要是 false 就要自己加
	@RequestMapping("/sayhello") 
	@ResponseBody 
	public String sayHello(@RequestParam(value = "name") String name, 
						   @RequestParam(value = "age", defaultValue = "0", required = false) Integer age) {
		return "hello " + name + ", " + age;
	}
	
	// 傳入 name(必要參數) 與 age(非必要參數) 參數
	// 子路徑: /demo/sayhelloTo?name=John&age=18
	// 子路徑: /demo/sayhelloTo?  <- 這裡因為有 default value 所以可以不用設定 name, age (也可以兩個都是 defaultvalue。)
	@RequestMapping("/sayhelloTo") 
	@ResponseBody 
	public String sayhelloTo(@RequestParam(value = "name", defaultValue = "unknown") String name, 
						   @RequestParam(value = "age", defaultValue = "0", required = false) Integer age) {
		return "sayhelloTo " + name + ", " + age;
	}
	
	// Lab: 
	// 子路徑: /demo/bmi?h=170&w=60
	// 輸出結果: bmi:20.76
	// 若沒有輸入參數則顯示: bmi: unknown
	@RequestMapping("/bmi") 
	@ResponseBody 
	// 也就是 @RequestParam 是設定請求參數的
	public String sayhelloTo(@RequestParam(value = "h", defaultValue = "0") Double h, 
						   @RequestParam(value = "w", defaultValue = "0") Double w) {
		if(h <= 0 || w<=0) {
			return "bmi: unknown";
		}
		
		double bmi = w / Math.pow(h/100, 2);
		return String.format("bmi: %.2f", bmi); // 格式化輸出(String.foramt())
	}
	
	//------------------------------------------- Chapter 14 (介紹各種情況) ----------------------------------------------- 
	// 要了解我們先配出完整 url(ex:/demo/any/abcdefg/java8 ), 再來思考如何寫程式
	
	// 路徑參數 PathVariable (很常使用)
	// 子路徑: /demo/exam/75 -> 結果: 75 pass
	// 子路徑: /demo/exam/45 -> 結果: 45 pass
	@RequestMapping("/exam/{score}")
	@ResponseBody // 不拼裝 return 出來的東西
	public String exam(@PathVariable("score") Integer score) { // 路徑參數: score 就是上面放進來的
	// The java string format() method returns the formatted string by given locale, format and arguments.
		return String.format("score: %d %s", score, (score>=60)?"Pass":"Fail");
	}
	
	/*
	 * Lab: PathVariable + RequestParam
	 * add 表示加法, sub 表示減法
	 * 路徑: /demo/calc/add?x=30&y=20 	-> 結果: 50 
	 * 路徑: /demo/calc/sub?x=30&y=20 	-> 結果: 10
	 * 路徑: /demo/calc/sub?y=20 		 	-> 結果: 20
	 * 路徑: /demo/calc/sub?x=0&y=20 	    -> 結果: -20
	 * 路徑: /demo/calc/add				-> 結果: 0
	 * 路徑: /demo/calc/abc				-> 結果: "None" <- 無此路徑
	 * 提示: 可以使用 Optional<Integer> 來取代 Integer
	 * 配置檔要加上 <mvc:annotation-driven /> 才可以使用 Optional<Integer>
	 *  */
	// Simply put, the annotation is used to map web requests to Spring Controller methods.
	// 下面印出在 web browser 不用 sout, 而是用 return
	// 學習 @PathVariable VS @ReqeustParam
	@RequestMapping("/calc/{exp}")
	@ResponseBody
	public String calc(@PathVariable("exp") String exp,
				@RequestParam(value = "x", required = false) Optional<Integer> x, 	// @RequestParam 是設定請求參數的
				@RequestParam(value = "y", required = false) Optional<Integer> y) {
		// 這裡只有在 x, 和 y 存在才執行, 否則會往下去跑後面的三個 if, 也就是說按照順序 if 判斷, 只要有一個 if 條件符合就 return, 後面的 if 就不會做了
		if(x.isPresent() && y.isPresent()) { // Optional 有 isPresent 可以用
					switch(exp) { // 判斷 exp 是 "add" 還是 "sub"
						case "add":
							return x.get() + y.get() + ""; // Optional 的 get 方法做"加法"運算
						case "sub":
							return x.get() - y.get() + ""; // 減法運算
						default:
							return "None";
					}
				}
		// 因為 exp 是　String, 所以不用轉 String
		if(!exp.equals("add") || !exp.equals("sub")) return "None"; // 如果輸入的不是 add / sub(如: abc), 輸出 null
		if(x.isPresent()) return x.get() + ""; // 這裡是如果 "只有 x 值"
		if(y.isPresent()) return y.get() + "";
		return "0";
	}
	// PathVariable 萬用字元 *(任意多字), ?(任意一字)
	// 路徑: /demo/any/abcdefg/java8   -> abcdefg 是可以隨便打的
	// 路徑: /demo/any/car/java7 
	// 統一印出: Java
	@RequestMapping("/any/*/java?") // ? 是 "任意一字", 你必須要加一個字, 但是可以隨便打(8,A,p...)
	@ResponseBody
	public String any() {
		return "Java";
	}
	
	// RequestParam 任意多組同名參數
	// 下面是 List 放入多組"數值"
	// 路徑: /demo/age?age=18&age=21&age=35 -> 多組 age(List)
	// 結果: 印出平均, 最大與最小
	@RequestMapping("/age") // http://localhost:8080/Springmvc/mvc/demo/age
	@ResponseBody
	public String age(@RequestParam("age") List<Integer> ageList) {
		// import java.util.IntSummaryStatistics (查 api: A state object for collecting statistics such as count, min, max, sum, and* average.) ~
		// IntSummaryStatistics 也就是定義一系列 count, min, max, sum 方法
		// Stream 方法(背起來用法): 都差不多, 都是用 stream(), mapToInt()..., 最後 summaryStatistics 傳回 IntSummaryStatistics
		// 背起來: 下面是怎麼把 list 用 stream() 拿出來
		IntSummaryStatistics stat = ageList.stream().mapToInt(Integer::intValue).summaryStatistics();
		return String.format("age: %.1f, max: %d, min: %d", stat.getAverage(), stat.getMax(), stat.getMin());
	}
	
	// Map: 參數 (key:value pair)
	// 路徑: /demo/score?chinese=100&english=80&math=70
	// 結果: sum: 250
	@RequestMapping("/score")
	@ResponseBody
	public String score(@RequestParam Map<String, String> scores) { // 鍵值對: 兩個都是 String
		// Entry: java.util.map
		//　int sum = scores.entrySet().stream().mapToInt(Entry::getValue).sum();  ->   錯誤(無法這樣轉)
		// 看下面解析
		int sum = scores.entrySet().stream().map(Entry::getValue).mapToInt(Integer::parseInt).sum();
		return String.format("sum: %d", sum); // int -> String
		
	}
	
	// Java pojo (Plan Old Java Object) 物件: 看下面解析
	// 我們這裡是把整個 User 物件放進來做處理, 驗證直接在 User 做
	// 路徑: /demo/user?name=Vincent&age=18
	@RequestMapping("/user")
	@ResponseBody
	// User(提示字)記得選 Springmvc, 傳進來的要是 User Entity
	public String addUser(@Valid User user, BindingResult result) { // -> validate User (bindingResult 查看下面解析)
		if(result.hasErrors()) { // hasErrors() methods inherited from interface validation.Errors
			return String.format("Add User Fail: %s", result); 
		}
		return String.format("Add OK: %s", user);
	}

	@RequestMapping("/Jquery")
	public String AJAX() { 
		return "Jquery/AJAX"; 
	}
}

// mapping: 映射
// @RequestMapping VS @ResponseBody

// POJO : 簡單並且純粹，POJO 就是一個 Java 物件只包含自己的屬性(private)和提取或儲存這些屬性的method(get、set) ~
// 沒有提供其他額外的方法, 而其他的 Object也是以 POJO 為基準開始延伸。
// Java POJO (Plan Old Java Object) 物件 -> 也就是說 POJO 包含 class, 屬性, getter/setter。

// Stream (Pipeline Operation):
// Laziness: 只要確認終端操作才開始輸送資料
// Source(來源, Collection / 檔案物件轉 Stream 物件) -> Intermediate Operation(中間操作) -> Terminal Operation(終端操作)
// 		Source: Collection.stream(), Stream.of(), Arrays.stream()
//		Intermediate Operation: filter, peek(), skip(), limit, distinct(), sorted(), map(), mapToInt(), flatMap()
//		Terminal Operation: forEach(), count(), sum(), average(), min(), max(), collect()


// int sum = scores.entrySet().stream().map(Entry::getValue).mapToInt(Integer::parseInt).sum();
//	利用 entrySet() 方法把 getValue 拿出來
//	map: 先轉字串(全部變 value 字串), mapToInt: value 再轉 Integer
//		scores.entrySet(): Map (轉)-> Collection
//		.stream(): Collection (轉)-> Stream

// BindingResult: 保存验证和绑定的结果，并包含可能发生的错误。 BindingResult必须紧随经过验证的模型对象之后，否则 Spring 无法验证该对象并引发异常








