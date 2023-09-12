package springmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import springmvc.entity.User;

@Service // 當你一創建 Service 類別, 馬上加上 @Service, 並且在 xml 作配置。
public class UserService {
	// 為何永遠用 List users = new ArrayList<>; 看下面解析:
	// 我們下面　新增，查詢＂方法都是使用這個 List 的(add, remove)，
	private static List<User> users = new ArrayList<>(); // 因為我們沒有資料庫, 這裡是先創建假的資料庫(ArrayList)在 Service 層
	
	public boolean create(User user) { // 傳進來的已經是 User 物件了
		Optional<User> optUser = getByName(user.getName()); // getName() 是 JavaBean 物件 getter 方法
		if(!optUser.isPresent()) { // 判斷是否存在, 不存在(重複)才加進資料庫
		users.add(user); // user 物件放進 users 資料庫
		}
		return !optUser.isPresent(); // boolean
	}
	
	public List<User> read() { // 直接取出整個物件 ArrayList
		return users;
	}
	
	public Optional <User> getByName(String name) { // 利用 name 取得對應的 Optional 容器(放 JavaBean 物件)
		// Stream(背起來用法, 都差不多): 用名字去資料庫取得指定的 JavaBean 物件(用 name 去找)
		Optional<User> optUser = users.stream().filter(u -> u.getName().equals(name)).findFirst(); // Optional 看下面解析
		return optUser; // 傳回對應的 Optional 物件, 供下面方法使用
	}
	public boolean updateAgeByName(String name, Integer newAge) { // 傳入 name 和 新的age 更改為 新的age
		Optional<User> optUser = getByName(name); // 取得對應的該物件
		// 如果有該物件, get() 取得 Optional 裡面的 Java Bean　物件
		if(optUser.isPresent()) {
			User user = optUser.get(); // Optional 的 get 方法: 取得 value
			user.setAge(newAge);
		}
		return optUser.isPresent(); // 回傳 true / false (這個回傳 boolean 不是在頁面顯示, 而是在 console 顯示)
	}
	public boolean deleteByName(String name) {
		Optional<User> optUser = getByName(name);
		if(optUser.isPresent()) {
			User user = optUser.get();
			users.remove(user); // 這個是 List 的 remove() 方法
		}
		return optUser.isPresent(); // 這個回傳 boolean 不是在頁面顯示, 而是在 console 顯示
	}
}
// 我們可以看出 "Service 層" 和 "Controller 層" 的 return 是不一樣的用法
//	Controller 的 return: URL
//  Service 的 return: 邏輯(isPresent())

// 為什麼是寫成 List list = new ArrayList() 而不是 ArrayList arrayList = new ArrayList()有如下的原因： 
// 		1.介面的好處: 當然你可以用 ArrayList list = new ArrayList() 但是一般不這麼用

//		2 設計模式中有對依賴倒置原則。程式要儘量依賴於抽象，不依賴於具體。 從Java語法上，這種方式是使用介面引用指向具體實現。~
//		比如，你若希望用LinkedList的實現來替代ArrayList的話，只需改動一行即可：List   list   =   new   LinkedList()；有點像 DAO Design Pattern ~
// 		也就是說 假設你開始用 ArrayList alist = new ArrayList(), 這下你有的改了，特別是如果你使用了 ArrayList 特有的方法和屬性。 ,如果沒有特別需求的話,
//		最好使用List list = new LinkedList(); ,便於程式程式碼的重構. 這就是面向介面程式設計的好處

//		3.面向介面程式設計: 提高程式寬展性,以後修改維護好些, 提高程式寬展性,以後修改維護好些


// Optional 用法:
//	 1. 最重要的是有 isPresent() 方法可以使用
// 	 2. 解決 null 問題, 因為 null 可以是「不存在」、「沒有」、「無」或「空」的概念，因此在應用時，總是令人感到模稜兩可, 所以 Optional 目的是做為 null 的替代方案
//   3. 這時Optional 即為該值的容器，若要取回該值，必須使用 get() 方法。get()：如果值存在就回傳這個值，否則就丟出 NoSuchElementException。
//   4. Optional 是一個 "物件"