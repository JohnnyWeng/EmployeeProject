package springmvc.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Person {	
	// @Size(min =2, max = 50, message = "姓名字數範圍必須介於 2~ 50 字之間")
	@Size(min =2, max = 50, message = "{person.name.range}") // {person.name.range}: 是從 errorMessages.properties 來的
	private String name; // 姓名
	
//	@NotNull(message = "年齡不可以是空值") // 年齡不能是 Null
//	@Range(min = 0, max = 150, message = "年齡範圍必須介於 0 ~150 歲之間")
	@NotNull(message = "{person.age.empty}") // 年齡不能是 Null
	@Range(min = 0, max = 150, message = "person.age.range")
	private Integer age; // 年齡(未來我們在寫 class 盡量不要用基本型別(int), 因為用物件型別有個 null 可以用)
	
	// @NotNull(message = "會員設定不可以是空值")
	@NotNull(message = "{person.member.empty}")
	private Boolean member; // 是否是會員
	
//	@NotNull(message = "生日不可以是空值")
//	@Past(message ="生日不可大於今日") // 連今天都不可以, 不可以大於今日
	@NotNull(message = "{person.birth.empty}")
	@Past(message ="{person.birth.past}") // 連今天都不可以, 不可以大於今日
	@JsonFormat(pattern = "yyyy-MM-dd") // 返回日期型態: 在驗證之前先寫返回型態 (傳給 view) -> 我們傳給 client 端就是 Json Format
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 接收日期型態 (接收 view 表單傳送過過來的日期資料)
	private Date birth; // 生日
	
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
	public Boolean getMember() {
		return member;
	}
	public void setMember(Boolean member) {
		this.member = member;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", member=" + member + ", birth=" + birth + "]";
	}
	
}
// 我們可以看出 Hibernate 最重要的就是很多不同功能的 @annotation 可以使用, 如果 Spring 不夠用就用 Hibernate 。
// 這裡最重要的是在 JavaBean 加上各種 Hibernate 的驗證 annotation。
// 上面的 message 是專門針對 @annotation 用的, 只是印出東西, 更重要的是背起來不同的 annotaion
