package springmvc.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springmvc.entity.Stock;
import springmvc.validator.StockValidator;

@Controller
@RequestMapping("/stock")
public class StockController {
	private List<Stock> stocks = new CopyOnWriteArrayList<>(); // 資料庫
	
	@Autowired
	private StockValidator stockValidator; // Autowired 自訂驗證
	
	@GetMapping("/")
	public String index(@ModelAttribute Stock stock, Model model) { // import 的是 Model.ui
		model.addAttribute("stocks", stocks);
		return "stock/index";
	}
	
	@PostMapping("/") // 加入要用 Post
	public String add(@Valid Stock stock, BindingResult result, Model model) { // 怕有錯誤, 所以使用 BindingResult
		
		// 手動驗證錯誤(自訂驗證)
		stockValidator.validate(stock, result); // 這裡多加了這行去做驗證
		if(result.hasErrors()) { // 有錯誤就回到首頁, 並且自動將錯誤訊息傳導到指定的 view 中
			model.addAttribute("stocks", stocks); // 小心有沒有加 s 
			return "stock/index";
		}
		stocks.add(stock); // 反之若沒有錯誤就回 Stock 畫面
		return "redirect:./";
	}
}
