package springmvc.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lotto") // 定義在類別外的 url 比較偏向是做定義頁面的名稱, 像這裡 /lotto 就表示跟 lotto 有關的頁面 
public class LottoController {
	// 存放 lotto 歷史紀錄
	// list 裡面放一個 set(一組樂透號碼)
	// 使用 set 因為樂透號碼不能重複
	// 我們都可以用 ArrayList 去模擬資料庫, 所以要宣告為 private
	private List<Set<Integer>> lottos = new ArrayList<>(); 
	
	// lotto 主畫面 (Http method =GET)
	// @RequestMapping(value= {"/", "/welcome"}, method = {ReuqestMethod.GET, RequestMethod.POST}) -> 下面是說連線可以是 / 或 /welcome。連線方式可以是 GET 或 POST
	// @RequestMapping(value= "/", , method = {ReuqestMethod.GET) -> 單一的
	// @GetMapping = @RequestMapping(method = RequestMethod.GET), 所以 @GetMapping 是精簡版
	// 假如都是 GET, value 都不用寫(也就是 Get 取得資料)
	// 首頁: http://localhost:8080/Springmvc/mvc/lotto/ -> 下面 @GetMapping("/") 就是網址列最後一個 /
	@GetMapping("/") 
	public String index(Model model) { // 主畫面
		model.addAttribute("lottos", lottos); // 把 lottos 這個 ArrayList 放進 model, 名稱叫做 lottos
		// forward(內網), 因為要建一個資料夾(lotto), 所以要在前面加上 lotto
		// lotto 底下的 index.jsp
		return "lotto/index"; 
	}
	
	//  新增/取得最新電腦選號
	// 下面 /add 其實根本不會顯示, 因為呼叫下面方法又會直接導引到 /addok
	@PostMapping("/add") // 我們新增習慣用 POST, 路徑就是 add。
	public String add(RedirectAttributes attr) {
		// 樂透 539 選號(1~39 取5個不重複的數字), 不重覆要用 set
		Set<Integer> lotto = getLottoNumIntegers();
		// 將 lotto 資料收入 lottos 集合中(set 放到 List 中)
		lottos.add(lotto);
		// 將 lotto 資料傳遞給 addOk.jsp 防止二次 submit
		attr.addAttribute("lotto", lotto);
		// attr.addFlashAttribute("lotto", lotto);
		// redirect: Client 端重導
		// forward: Server 端重導 (直接指向特定 jsp) -> return...
		return "redirect:addOk"; // redirect(外網): 不是直接給下面的 getMapping, 而是先給使用者,然後給 JSP(也就是連兩次)
	}
	// url: http://localhost:8080/Springmvc/mvc/lotto/addOk。回傳 lotto/success.jsp
	@GetMapping("addOk") // 因為上面是用 redirect 所以要用 Get
	public String success() {
		return "lotto/success"; // forward(內網)
	}
	// 這個因為是我們的商業邏輯, 所以要 private 起來
	private Set<Integer> getLottoNumIntegers() {
		Random r = new Random();
		Set<Integer> lotto = new LinkedHashSet<>();
		while(lotto.size() < 5) {
			lotto.add(r.nextInt(39) + 1); // r.nextInt(39) -> 隨機產生 0 ~ 39 的整數
		}
		return lotto;
	}
}
// lotto.add(): Set 方法 , Adds the specified element to this set if it is not already present (optional operation).
