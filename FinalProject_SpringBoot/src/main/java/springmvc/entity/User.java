package springmvc.entity;

import javax.validation.constraints.NotNull; // 可以看到 javax, 也就是 Spring 也支援(javax: 原生套件)
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range; // 因為上面原生的 javax 沒有 Range 的 validation, 所以才使用 Hibernate 的 annotation 設定值
// 我們用 Hibernate 做驗證是在這個 JavaBean 做, 在"屬性"作 validate
public class User {
	// JavaBean(Entity) -> 都是從屬性開始寫, 然後剩下的就直接用精靈建立 getter/setter
	@NotNull(message = "User name not null") // Hibernate 驗證 -> null 驗證 (未輸入名字會報錯)
	@Size(min = 2, max = 20, message = "name out of range") // 名字長度限制 2位 ~ 20位
	private String name;
	
	@NotNull(message = "User age not null") 
	// 在 Spring 做檢驗 Bean 的時候"都會使用 hibernate"
	@Range(min = 0, max = 150, message = "out of range") // Hibernate 驗證 -> 範圍驗證
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age; 
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
	
}
// Spring 是一個整合的框架(整合 Strout, Hibernate, Spring…), 有一些檢驗的參數 Springframework 有, 有些 Hibernate 沒有, 反之亦然, 也就是說 Spring 沒有的就去第三方找。~
// 就變成看你熟悉甚麼框架, Spring 都有。所以你做 valitation 有兩種不同的套件。所以你做 valitation 有兩種不同的套件,因為 Spring 兼容所有套件。
// 這樣我們就不用寫一大堆 if-else, 直接 annotation 就好了。
// 可以看出 Springboot 其實強大之處在於可以有很多 annotation 使用, 取代 implements Contoller, Service, if-else...。
