package springmvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springmvc.entity.Stock;
import yahoofinance.YahooFinance;

// 要注意 Stock, Validator... 都有很多種 import, 要小心 import 的東西

// Validator 不要 import 錯了, 是 org.springframework.validation.Validator

// 要給 Spring 管理就要加上 annotation, 也就是說要加上 @Component, 你可以加上 @Service, @repository ~
// 但是像這裡"無法歸類的"情況就要用 @Component 
@Component 
public class StockValidator implements Validator{ // 去實作 Validator

	@Override
	public boolean supports(Class<?> clazz) {
		// 判斷 clazz 是否是要驗證的類
		return Stock.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(!(target instanceof Stock)) { // 在做轉型前最好做一個判斷(第二重保障) -> 其實上面已經做判斷了, 這裡再做一次
			return;
		}
		Stock stock = (Stock)target;
		// 基礎驗證: 可以使用 ValidationUtils -> 一看到 utils 就知道是好用的東西(有很多方法可以用)
		// "stock.symbol.empty" 會先去找 errorMessage.proterties。如果是 "stock.symbol.empty1" 也就不是錯誤的訊息, 就只會印出這個字串, 而不是錯誤訊息。
		// rejectIfEmptyOrWhitespace: Reject the given field with the given error code if the value is empty or just contains whitespace.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "symbol", "stock.symbol.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "stock.price.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "stock.amount.empty");
		
		// 進階驗證
		yahoofinance.Stock yStock = null; // 之所以用 yahoofinance.Stock 因為我們有 Stock.java, 所以要用全名 yahoofinance
		try {
			yStock = YahooFinance.get(stock.getSymbol());
			double previousClose = yStock.getQuote().getPreviousClose().doubleValue();// 昨天收盤價
			// 買進價格必須是昨收的±10%之間
			if(stock.getPrice() < previousClose * 0.9 || stock.getPrice() > previousClose * 1.1) {
				// 不能用 reject, 而是用 rejectValue, 因為 rejectValue 支援 .properties
				errors.rejectValue("price", "stock.price.range");
				// 顯示範圍, 這裡要用 reject 才能把字串秀出來
				errors.reject("price", String.format("(%.1f ~ %1f)",
												(previousClose * 0.9),  
												(previousClose * 1.1))
						);
			}
			// 買進股數必須大於或等於1000
			if(stock.getAmount() < 1000) {
				errors.rejectValue("amount", "stock.amount.notenough");
			}
			// 買進股數必須是1000的倍數(1000股 = 1張)
			if(stock.getAmount() % 1000 != 0) {
				errors.rejectValue("amount", "stock.amount.range");
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // 如果有錯誤訊息就要印出來
			if(yStock == null) {
				// 要用 rejectValue 否則跑不出來
				errors.rejectValue("symbol", "stock.symbol.notfound");
				// ValidationUtils.reject... 你也可以寫成像上面那樣
			}
		}
	} 
	
	
}
